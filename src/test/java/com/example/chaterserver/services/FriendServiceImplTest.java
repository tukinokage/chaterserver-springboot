package com.example.chaterserver.services;

import com.example.chaterserver.Application;
import com.example.chaterserver.entity.FriendContact;
import com.example.chaterserver.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FriendServiceImplTest {

    @Autowired
    FriendService friendService;

    @Test
    public void testApplyFriend(){
        FriendContact friendContact = new FriendContact("123", "1");
        friendService.applyForFriend(friendContact);
    }

    @Test
    public void testConfirmFriend(){
        FriendContact friendContact = new FriendContact("123", "1");
        friendService.confrimNewFriend(friendContact);
    }

    @Test
    public void testCancelFriend(){
        FriendContact friendContact = new FriendContact("123", "1");
        friendService.cancelNewFriend(friendContact);
    }

    @Test
    public void testLoadFriend(){
        User user = new User();
        user.setUserId("123");
        List<User> list = friendService.findFriend(user);
        for (User u:
             list) {
            System.out.println(u.getUserNameString());
        }
    }





}