package com.example.chaterserver.services;

import com.example.chaterserver.entity.FriendContact;
import com.example.chaterserver.entity.User;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.List;

public interface FriendService {
        //user不包括password
        ArrayList<FriendContact> loadFriends(User user);
        ArrayList<User> findFriend(User user);
        boolean DeletedFriend(FriendContact friendContact);
        boolean confrimNewFriend(FriendContact friendContact);
        boolean cancelNewFriend(FriendContact friendContact);
        boolean applyForFriend(FriendContact friendContact);
        }
