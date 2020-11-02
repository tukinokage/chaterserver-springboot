package com.example.chaterserver.services;


import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    /**@return token str
     *
     * */
    @Override
    public String login(String name, String psw) {
        /*
        * 此处取出用户数据
        *
        * */
        if (Objects.equals("查出来的name", name) &&
            Objects.equals("查出来的psw" , psw) ){
                getToken()

        }



        return ;
    }

    @Override
    public String getToken(String name, String psw) {
        return null;
    }

}
