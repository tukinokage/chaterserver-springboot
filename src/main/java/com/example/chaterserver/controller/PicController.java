package com.example.chaterserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.chaterserver.app.WebSocketFilter;
import com.example.chaterserver.bean.DownloadMessage;
import com.example.chaterserver.entity.Picture;
import com.example.chaterserver.enums.typeenum.FileTypeEnum;
import com.example.chaterserver.services.PicturesService;
import com.example.chaterserver.enums.typeenum.MessageTypeEnum;
import com.example.chaterserver.util.Base64Uitl;
import com.example.chaterserver.util.FilePath;
import com.example.chaterserver.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.FileAttribute;

@Controller
@RequestMapping("/pic")
public class PicController {

    @Autowired
    private PicturesService picturesService;

   /* @Value("${chat.image.path}")
    private static String IMG_PATH;*/

    @RequestMapping(value = "/upload", method = RequestMethod.POST )
    @ResponseBody
    public String uploadPic(@RequestParam("info") String infoStr, @RequestParam("file") String picDataStr) throws IOException {

        System.out.println(infoStr);
        String resJsonStr = "";
        boolean saveresult = false;


        JSONObject responseJson = new JSONObject();
        JSONObject picInfoJson = JSONObject.parseObject(infoStr);


        Picture picInfo = JSON.toJavaObject(picInfoJson, Picture.class);
        picInfo.setDateTime(TimeUtil.getDateTime());

        //尝试存储, 直到成功或者5次
        int i = 0;
        while( saveresult == false && i<5){
            i++;
            File file = new File(FilePath.IMG_PATH.CHAT_IMG_PATH);
            System.out.println( "查看文件" + file.exists());
            //pic name由picid组成
            saveresult = Base64Uitl.generateImg(picDataStr,
                    FilePath.IMG_PATH.CHAT_IMG_PATH
                                + "/"
                                + picInfo.getPicId() + ".jpg");

            if (saveresult){
                break;
            }

        }

        if(saveresult){

            DownloadMessage downloadMessage = new DownloadMessage();
            downloadMessage.setFileName(picInfo.getPicId() + ".jpg");
            downloadMessage.setFromId(picInfo.getSendId());
            downloadMessage.setToId(picInfo.getRecId());
            downloadMessage.setFileType(String.valueOf(FileTypeEnum.CHAT_PICTURE.getTypeNum()));
            JSONObject newJsonObject = (JSONObject) JSON.toJSON(downloadMessage);
            newJsonObject.put("MessageType", String.valueOf(MessageTypeEnum.DOWNLOAD_MESSAGE.getTypeNum()));

            resJsonStr = JSON.toJSONString(newJsonObject);

            //发送信息给目标客户
            WebSocketFilter.sessionPools.get(downloadMessage.getToId()).getBasicRemote().sendText(resJsonStr);

            responseJson.put("errorMsg", "0");
        }else {
            responseJson.put("errorMsg", "服务器出错");
        }

        return responseJson.toJSONString();
    }


    @RequestMapping(value = "/download", method = RequestMethod.POST )
    @ResponseBody
    public String download(@RequestBody String json) throws IOException {
            JSONObject responseJson = JSONObject.parseObject(json);
            String downloadMsg = responseJson.getString("downloadMsg");
            /*
             *
             * 此处匹配token
             *
             * */

            if(responseJson.isEmpty()){
                responseJson.put("errorMsg", "服务器无接受到下载信息");
                return responseJson.toJSONString();
            }

            try {

                DownloadMessage downloadMessage = JSONObject.parseObject(downloadMsg, DownloadMessage.class);
                String dataStr = Base64Uitl.GetImageStr(FilePath.IMG_PATH.CHAT_IMG_PATH
                        + "/"
                        + downloadMessage.getFileName());

                responseJson.put("errorMsg", "0");
                responseJson.put("file", dataStr);
            } catch (Exception e) {
                responseJson.put("errorMsg", "下载出错");
            }finally {
                System.out.println(responseJson.toJSONString());
                return responseJson.toJSONString();
            }

        }


    }
