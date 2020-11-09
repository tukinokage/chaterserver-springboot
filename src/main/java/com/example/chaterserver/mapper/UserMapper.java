package com.example.chaterserver.mapper;


import com.example.chaterserver.entity.ClientMessage;
import com.example.chaterserver.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    int insert(User user);

    int queryNum(User user);
    List<User> query(User user);
}
