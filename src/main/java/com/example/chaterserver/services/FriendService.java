package com.example.chaterserver.services;

import com.example.chaterserver.entity.Friendcontact;
import com.example.chaterserver.entity.User;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.List;

public interface FriendService {

    //user不包括password
    ArrayList<Friendcontact> loadFriends(User user);
    ArrayList<User> findFriend(User user);
    boolean DeletedFriend(Friendcontact friendcontact);
    boolean confrimNewFriend(Friendcontact friendcontact);
    boolean cancelNewFriend(Friendcontact friendcontact);

    boolean applyForFriend(Friendcontact friendcontact);
}
