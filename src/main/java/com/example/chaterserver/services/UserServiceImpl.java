package com.example.chaterserver.services;


import com.example.chaterserver.app.AuthorizationInterceptor;
import com.example.chaterserver.entity.User;
import com.example.chaterserver.mapper.UserMapper;
import com.example.chaterserver.redisCache.RedisUtil;
import com.example.chaterserver.util.MD5CodeCeator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserMapper userMapper;

    /**@return token str
     * */

    @Override
    public String login(String name, String psw) {
        String token = "";

        User user = new User();
        user.setPassword(psw);
        user.setUserName(name);

        try {
            int nums = userMapper.queryNum(user);

            if (nums > 0){
                //清除上次的token 记录
                String oldToken = (String) redisUtil.get(name);
                if (oldToken != null && !oldToken.equals("")){
                    redisUtil.del(oldToken);
                }

                String rtoken = MD5CodeCeator.randomUUID();

                Boolean a = redisUtil.set(rtoken, name, AuthorizationInterceptor.TOKEN_EXPIRE_TIME);
                Boolean b = redisUtil.set(name, rtoken, AuthorizationInterceptor.TOKEN_EXPIRE_TIME);
                if (a && b){
                    token = rtoken;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return token;
        }
    }

    @Override
    public String register(String name, String psw) {
        return null;
    }

    @Override
    public User getUserById(String id) {
        return null;
    }

    @Override
    public User getUserByName(String name) {
        return null;
    }

}
