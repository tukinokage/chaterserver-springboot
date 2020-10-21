package com.example.chaterserver.controller;

import com.example.chaterserver.entity.ClientMessage;
import com.example.chaterserver.services.ChatMessageService;
import com.example.chaterserver.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class IndexController {

    @Autowired
    private ChatMessageService chatMessageService;

    @RequestMapping(value = "/index")
    @ResponseBody
    public String index(){

        ClientMessage clientMessage = new ClientMessage();
        clientMessage.setFromID("123");
        clientMessage.setHasPic(0);
        clientMessage.setMessageId("aabbccdd12313");
        clientMessage.setSendTime(TimeUtil.getDateTime());
        clientMessage.setTextContent("测试信息");
        clientMessage.setToID("123");

        chatMessageService.insert(clientMessage);


        return "fuck";
    }
}
