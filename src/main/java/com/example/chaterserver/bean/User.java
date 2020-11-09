package com.example.chaterserver.bean;

import org.springframework.context.annotation.Bean;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String userId;
    private String headImg_url;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserNameString() {
        return userName;
    }
    public String getHeadImgString() {
        return headImg_url;
    }

    public String getUserIdString() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private void setHeadImg(String headImg_url) {
        this.headImg_url = headImg_url;
    }

}
