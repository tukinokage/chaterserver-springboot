package com.example.chaterserver.app;

import com.alibaba.fastjson.JSON;
import com.example.chaterserver.entity.ClientMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@ServerEndpoint(value="/ws/{uid}")
@Component
public class WebSocketFilter {

    /** 记录当前在线连接数 */
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    /**
     * 连接建立成功
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "uid") String uid) {
        onlineCount.incrementAndGet();
        // 在线数加1
        //加入状态池
        sessionPools.put(uid, session);

       // Log.info("有新连接加入：{}，当前在线人数为：{}" + session.getId() + onlineCount.get() + uid);
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(Session session) {
        onlineCount.decrementAndGet(); // 在线数减1
       // Log.info("有一连接关闭：{}，当前在线人数为：{}" + session.getId() + onlineCount.get());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *  客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(message);
            ClientMessage newClientMessage = JSON.parseObject(message, ClientMessage.class);

            for (Map.Entry<String, Session> sessionEntry:
                    sessionPools.entrySet()) {
                if(sessionEntry.getKey().equals(newClientMessage.getToID())){
                    sendMessage(message, sessionEntry.getValue());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Log.info("服务端收到客户端[{}]的消息:{}"+ session.getId()+message);

    }

    @OnError
    public void onError(Session session, Throwable error) {
       System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
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
