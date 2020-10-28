package com.example.chaterserver.controller;

import com.example.chaterserver.entity.ClientMessage;
import com.example.chaterserver.enums.timeenum.Status;
import com.example.chaterserver.redisCache.RedisUtil;
import com.example.chaterserver.services.ChatMessageService;
import com.example.chaterserver.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


@Controller
public class IndexController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    RedisUtil redisUtil;

    @RequestMapping(value = "/index")
    @ResponseBody
    public String index(){

        Map<String, Object> hashMap = new HashMap<>();
        String key = "test1";
        hashMap.put("privateKey", "123456");
        long time = 7L * 60 * 60 * 24;
        boolean result = redisUtil.hmset(key, hashMap, time);

        Map<Object, Object> hmgetMap = redisUtil.hmget(key);

        return String.valueOf(result) + hmgetMap.get("privateKey");
    }
}
