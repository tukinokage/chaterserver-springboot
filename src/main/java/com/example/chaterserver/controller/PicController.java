package com.example.chaterserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.chaterserver.entity.DownloadMessage;
import com.example.chaterserver.entity.Picture;
import com.example.chaterserver.services.PicturesService;
import com.example.chaterserver.services.PicturesServiceImpl;
import com.example.chaterserver.util.Base64Uitl;
import com.example.chaterserver.util.FilePath;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/pic")
public class PicController {

    @Autowired
    private PicturesService picturesService;

    @RequestMapping("/upload")
    @ResponseBody
    public String uploadPic(@RequestBody String jsonStr){

        String resJsonStr = "";
        boolean saveresult = false;
        int i = 0;

        JSONObject jsonObject = JSONObject.parseObject(jsonStr);

        JSONObject picInfoJson = (JSONObject) jsonObject.get("picInfo");

        String base64Pic = jsonObject.getString("data");
        Picture picInfo = JSON.toJavaObject(picInfoJson, Picture.class);

        //尝试5次存储
        while( saveresult == false && i<5){
            i++;
            //pic name由picid组成
            saveresult = Base64Uitl.generateImg(base64Pic,
                        FilePath.IMG_PATH.CHAT_IMG_PATH
                                + picInfo.getPicId() + ".jpg");


        }

        if(saveresult){
            DownloadMessage downloadMessage = new DownloadMessage();
            downloadMessage.setFileName(picInfo.getPicId() + ".jpg");
            downloadMessage.setFromId(picInfo.getSendId());
            downloadMessage.setToId(picInfo.getRecId());
            resJsonStr = JSON.toJSONString(downloadMessage);

        }

        return resJsonStr;
    }
}
