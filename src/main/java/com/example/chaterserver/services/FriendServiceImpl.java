package com.example.chaterserver.services;

import com.example.chaterserver.entity.Friendcontact;
import com.example.chaterserver.entity.User;
import com.example.chaterserver.mapper.FriendMapper;
import com.example.chaterserver.mapper.UserMapper;
import com.example.chaterserver.redisCache.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.List;
@Component
@Service("friendService")
public class FriendServiceImpl implements FriendService {

    @Autowired
    FriendMapper friendMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public ArrayList loadFriends(User user) {

        ArrayList<User> friends = new ArrayList<>();
        ArrayList<Friendcontact> friendcontactArrayList = friendMapper.selectContact(user);
        for (Friendcontact f:
             friendcontactArrayList) {
            User queryU = new User();
            queryU.setUserId(f.getFriendId());
            List<User> queryResult = userMapper.query(queryU);
            User friend = queryResult.get(0);
            friends.add(friend);
        }
        return friends;
    }

    @Override
    public ArrayList findFriend(User user) {
        ArrayList<User> users = friendMapper.find(user);

        return users;
    }

    @Transactional
    @Override
    public boolean DeletedFriend(Friendcontact friendcontact) {
        boolean flag = false;
        //删除双向关系使用
        Friendcontact friendcontact2 = new Friendcontact(friendcontact.getFriendId(), friendcontact.getUserId()){

        };
        if(friendMapper.delete(friendcontact) > 0) {

            if (friendMapper.delete(friendcontact2) > 0){
                flag = true;
            }else {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }

        }
        return flag;
    }

    @Override
    public boolean confrimNewFriend(Friendcontact friendcontact) {

        return false;
    }

    @Override
    public boolean cancelNewFriend(Friendcontact friendcontact) {
        return false;
    }

    @Override
    public boolean applyForFriend(Friendcontact friendcontact) {

        boolean flag = false;
        User queryUser = new User();
        queryUser.setUserId(friendcontact.getFriendId());

        if(userMapper.queryNum(queryUser) > 0){

        }
        return flag;
    }


}
