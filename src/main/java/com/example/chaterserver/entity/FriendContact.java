package com.example.chaterserver.entity;

import java.io.Serializable;

public class FriendContact implements Serializable {

    String userId;



    String friendId;

    public FriendContact(){

    }

    public FriendContact(String userId, String friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

}
