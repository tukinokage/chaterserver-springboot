package com.example.chaterserver.services;

import com.example.chaterserver.entity.ClientMessage;

import java.util.List;

public interface ChatMessageService {
    int insert(ClientMessage message);

    List<ClientMessage> query(ClientMessage message);
}
