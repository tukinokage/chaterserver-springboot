package com.example.chaterserver.services;

import com.example.chaterserver.entity.FriendContact;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        ArrayList<FriendContact> friendContactArrayList = friendMapper.selectContact(user);
        for (FriendContact f:
             friendContactArrayList) {
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
    public boolean DeletedFriend(FriendContact friendContact) {
        boolean flag = false;
        //删除双向关系使用
        FriendContact friendContact2 = new FriendContact(friendContact.getFriendId(), friendContact.getUserId()){

        };
        if(friendMapper.delete(friendContact) > 0) {

            if (friendMapper.delete(friendContact2) > 0){
                flag = true;
            }else {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }

        }
        return flag;
    }

    @Transactional
    @Override
    public boolean confrimNewFriend(FriendContact friendContact) {

        String key = friendContact.getUserId() +friendContact.getFriendId();
        if (redisUtil.hasKey(key)){
            /*生成双向关系*/
            FriendContact friendContact2 = new FriendContact(friendContact.getFriendId(), friendContact.getUserId());
            if(friendMapper.insert(friendContact) > 0){
                if(friendMapper.insert(friendContact2) > 0){
                    redisUtil.del(key);
                    return true;
                }else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
            }
        }
        return false;
    }

    @Override
    public boolean cancelNewFriend(FriendContact friendContact) {
        String key = friendContact.getUserId() + friendContact.getFriendId();
        if (redisUtil.hasKey(key)){
            redisUtil.del(key);
            return true;
        }

        return false;
    }

    /***生成申请****/
    @Override
    public boolean applyForFriend(FriendContact friendContact) {

        boolean flag = false;
        User queryUser = new User();
        queryUser.setUserId(friendContact.getFriendId());

        if(userMapper.queryNum(queryUser) > 0){

            Map<String, Object> map = new HashMap<>();
            map.put(friendContact.getUserId(), friendContact.getFriendId());

            String key = friendContact.getUserId() + friendContact.getFriendId();
            flag = redisUtil.hmset(key, map);
        }
        return flag;
    }


}
