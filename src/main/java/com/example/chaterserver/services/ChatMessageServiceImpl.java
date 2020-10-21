package com.example.chaterserver.services;

import com.example.chaterserver.entity.ClientMessage;
import com.example.chaterserver.mapper.ClientMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service("chatMessageService")
public class ChatMessageServiceImpl implements ChatMessageService{

       @Autowired
       private ClientMessageMapper clientMessageMapper;

        @Override
        public int insert(ClientMessage message)
        {
            int i = 0;
            try {
                i = clientMessageMapper.insert(message);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(i);
            }
           // System.out.println(clientMessage.getTextContent());
                return 1;
        }
}
