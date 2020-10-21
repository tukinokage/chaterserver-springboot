package com.example.chaterserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.chaterserver.entity.Picture;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/pic")
public class PicController {
    @RequestMapping("/upload")
    @ResponseBody
    public String uploadPic(@RequestBody String jsonStr){
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);

        JSONObject picInfoJson = (JSONObject) jsonObject.get("picInfo");
        jsonObject.get("data");
        Picture picInfo = JSON.toJavaObject(picInfoJson, Picture.class);


        return jsonStr;
    }
}
