package com.example.chaterserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.chaterserver.redisCache.RedisUtil;
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

    /**@return JSONString 包含token和userId两个键值对，如果为""则代表失败
     *
     * */
    @RequestMapping(value = "/login")
    @ResponseBody
    public String Login(@RequestBody String json){
        System.out.println(json);
        JSONObject jsonObject = (JSONObject) JSONObject.parse(json);
        JSONObject resultJson = new JSONObject();
        resultJson.put("errorMsg", "");
        if (userService.isExistUserByName(jsonObject.getString("name"))){
            //登录
            resultJson =  userService.login(jsonObject.getString("name"), jsonObject.getString("password"));
        }else {
            //注册
            resultJson = userService.register(jsonObject.getString("name"), jsonObject.getString("password"));
        }

        System.out.println("token:" + resultJson.toJSONString());
        return resultJson.toJSONString();
    }


    /**@return username ""则失效;
     * erroMsg为""、userName不为""为登陆成功
     *
    * */
        @RequestMapping(value = "/loginToken")
        @ResponseBody
        public String Register(@RequestBody String json){
            System.out.println(json);
            JSONObject jsonObject = (JSONObject) JSONObject.parse(json);
            JSONObject resultJSONObject = new JSONObject();
            resultJSONObject.put("errorMSg", "");
            resultJSONObject.put("username", "");

            String tokenStr =  jsonObject.getString("token");
            String userName = userService.loginByToken(tokenStr);
            if("".equals(userName) || userName == null){
                resultJSONObject.put("errorMSg", "登陆过期，请重新登陆");
            }else {
                resultJSONObject.put("username", userName);
            }

            System.out.println("userName:" + userName);
            return resultJSONObject.toJSONString();
    }

}
