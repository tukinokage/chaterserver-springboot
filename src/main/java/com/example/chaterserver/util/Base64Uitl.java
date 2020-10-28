package com.example.chaterserver.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

public class Base64Uitl {


    public static boolean generateImg(String base64data, String filePathAndName){
        if (base64data == null || base64data.equals("")){
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();


        try{
            File newFile = new File(filePathAndName);

            if(!newFile.getParentFile().exists()){
                //如果目标文件所在的目录不存在，则创建父目录
                newFile.getParentFile().mkdirs();
            }


            // Base64解码,对字节数组字符串进行Base64解码并生成文件
            byte[] b = decoder.decodeBuffer(base64data);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成文件
            OutputStream out = new FileOutputStream(filePathAndName);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }


// 图片转化成base64字符串
    public static String GetImageStr(String filePathAndName) {// 将文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(filePathAndName);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }



}




