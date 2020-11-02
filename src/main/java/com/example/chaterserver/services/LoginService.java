package com.example.chaterserver.services;

public interface LoginService {
    String login(String name, String psw);

    String getToken(String name, String psw);
}
