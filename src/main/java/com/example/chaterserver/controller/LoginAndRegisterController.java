package com.example.chaterserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.chaterserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ls")
public class LoginAndRegisterController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login")
    @ResponseBody
    public String Login(@RequestBody String json){
        System.out.println(json);
        JSONObject jsonObject = (JSONObject) JSONObject.parse(json);
        String token =  userService.login(jsonObject.getString("name"), jsonObject.getString("password"));
        System.out.println("token:" + token);
        return token;
    }

}
