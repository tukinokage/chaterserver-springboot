package com.example.chaterserver.dao;


import com.example.chaterserver.entity.ClientMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ClientMessageDao {

    ClientMessage insert(ClientMessage clientMessage);
}
