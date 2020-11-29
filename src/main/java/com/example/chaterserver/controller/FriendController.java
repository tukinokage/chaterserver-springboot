package com.example.chaterserver.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.chaterserver.entity.User;
import com.example.chaterserver.services.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/friend")
public class FriendController {

    @Autowired
    FriendService friendService;

    @RequestMapping(value = "findFriend")
    @ResponseBody
    public String findFriend(@RequestBody String json){

        JSONObject resultJson = new JSONObject();
        resultJson.put("errorMsg", "");

        try {
            JSONObject resquestJson = JSON.parseObject(json);
            String seacrhName = resquestJson.getString("username");
            User user =new User();
            user.setUserName(seacrhName);

            List<User> list = (ArrayList<User>)friendService.findFriend(user);
            resultJson.put("data", list);
        }catch (Exception e){
            e.printStackTrace();
            resultJson.put("errorMsg", "服务器处理出错");
        }

        return resultJson.toJSONString();
    }
}
