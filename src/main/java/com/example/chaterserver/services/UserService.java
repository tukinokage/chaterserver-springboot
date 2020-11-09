package com.example.chaterserver.services;

import com.example.chaterserver.entity.User;

public interface UserService {
    String login(String name, String psw);

    String register(String name, String psw);

    User getUserById(String id);
    User getUserByName(String name);

}
