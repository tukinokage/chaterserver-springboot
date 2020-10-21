package com.example.chaterserver.mapper;

import com.example.chaterserver.entity.ClientMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ClientMessageMapper {
    int insert(ClientMessage clientMessage);

    ClientMessage query(ClientMessage clientMessage);
}
