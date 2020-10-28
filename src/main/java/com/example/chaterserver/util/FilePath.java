package com.example.chaterserver.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FilePath {
    @Value("${file.root.path}")
    private static String rootPath;

    @Value("${chat.content.image.path")
    private static String chatContentImgPath;

    public static class IMG_PATH {
        //public static final String CHAT_IMG_PATH = "/home/chat_server/chat_img";
        public static final String CHAT_IMG_PATH = "D://home/chat_server/chat_img";
       // public static final String CHAT_IMG_PATH =  chatContentImgPath;
    }
}
