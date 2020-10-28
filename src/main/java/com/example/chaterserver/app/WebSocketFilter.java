package com.example.chaterserver.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.chaterserver.entity.ClientMessage;
import com.example.chaterserver.services.ChatMessageService;
import com.example.chaterserver.enums.typeenum.MessageTypeEnum;
import com.example.chaterserver.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ServerEndpoint(value="/ws/{uid}")
public class WebSocketFilter {

    private static AtomicInteger onlineCount = new AtomicInteger(0);

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    public static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    //private static ConcurrentHashMap<String, ChatMessageService> daoServicePools = new ConcurrentHashMap<>();

    //websocket运行时是多个实例，spring容器是单例，只注入一次，故采用静态注入
    static ChatMessageService chatMessageService;

    @Autowired
    public void setChatMessageService(ChatMessageService chatMessageService){
        WebSocketFilter.chatMessageService = chatMessageService;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "uid") String uid) {
        onlineCount.incrementAndGet();
        // 在线数加1
        //加入状态池a
        sessionPools.put(uid, session);
        //ChatMessageService chatMessageService = new ChatMessageServiceImpl();
       // daoServicePools.put(session.getId(), chatMessageService);

       // Log.info("有新连接加入：{}，当前在线人数为：{}" + session.getId() + onlineCount.get() + uid);
    }


    @OnClose
    public void onClose(Session session) {
        onlineCount.decrementAndGet(); // 在线数减1
       // Log.info("有一连接关闭：{}，当前在线人数为：{}" + session.getId() + onlineCount.get());

       // daoServicePools.remove(session.getId());
    }


    @OnMessage
    public void onMessage(String message, Session session) {

        try {
            System.out.println(message);

            JSONObject jsonObject = JSONObject.parseObject(message);
            ClientMessage newClientMessage = JSON.parseObject(message, ClientMessage.class);
            //服务器端生成日期
            newClientMessage.setSendTime(TimeUtil.getDateTime());

           // ChatMessageService chatMessageService = daoServicePools.get(session.getId());
            int messageTypeNum = jsonObject.getInteger("MessageType");
           MessageTypeEnum typeEnum =  MessageTypeEnum.getMessageTypeEnumByTypeNum(messageTypeNum);

            for (Map.Entry<String, Session> sessionEntry:
                    sessionPools.entrySet()) {

                if(sessionEntry.getKey().equals(
                        newClientMessage.getToID()
                )) {
                    switch (typeEnum){
                        case CHAT_MESSAGE:
                            System.out.println("开始存储");
                            System.out.println(chatMessageService == null);
                            chatMessageService.insert(newClientMessage);
                            sendMessage(message, sessionEntry.getValue());
                            break;
                        default:
                            break;
                    }



                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("转存出错");
        }

        // Log.info("服务端收到客户端[{}]的消息:{}"+ session.getId()+message);

    }

    @OnError
    public void onError(Session session, Throwable error) {
       System.out.println("发生错误");
        error.printStackTrace();
    }




    private void sendMessage(String message, Session toSession) {
        try {
         //   Log.info("服务端给客户端[{}]发送消息{}"+ toSession.getId()+ message);
            System.out.println("发送");
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            System.out.println("服务端发送消息给客户端失败");
        }
    }
}
