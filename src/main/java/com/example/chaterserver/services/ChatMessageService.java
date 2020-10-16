package com.example.chaterserver.services;

import com.example.chaterserver.entity.ClientMessage;

public interface ChatMessageService {
    int insert(ClientMessage message);
}
