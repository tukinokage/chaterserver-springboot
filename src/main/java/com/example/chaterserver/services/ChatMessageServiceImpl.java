package com.example.chaterserver.services;

import com.example.chaterserver.dao.ClientMessageDao;
import com.example.chaterserver.entity.ClientMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("chatMessageService")
public class ChatMessageServiceImpl implements ChatMessageService{

        @Autowired
        ClientMessageDao clientMessageDao;

        @Override
        public int insert(ClientMessage message) {
                return 0;
        }
}
