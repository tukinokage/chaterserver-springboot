package com.example.chaterserver.controller;

import com.example.chaterserver.redisCache.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ls")
public class LoginAndRegisterController {

    @RequestMapping(value = "/login")
    @ResponseBody
    public String Login(){

    }
}
