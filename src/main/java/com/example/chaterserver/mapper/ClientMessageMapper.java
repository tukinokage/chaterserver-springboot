package com.example.chaterserver.mapper;

import com.example.chaterserver.entity.ClientMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClientMessageMapper {
    int insert(ClientMessage clientMessage);

    List<ClientMessage> query(ClientMessage clientMessage);
}
