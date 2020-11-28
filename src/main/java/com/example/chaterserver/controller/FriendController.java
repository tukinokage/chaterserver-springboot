package com.example.chaterserver.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.chaterserver.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/friend")
public class FriendController {

    @RequestMapping(value = "findFriend")
    @ResponseBody
    public String findFriend(@RequestBody String json){

        JSONObject resultJson = new JSONObject();
        resultJson.put("errorMsg", "");

        JSONObject resquestJson = JSON.parseObject(json);
        String seacrhName = resquestJson.getString("username");
        User user =new User();
        user.setUserName(seacrhName);

    }
}
