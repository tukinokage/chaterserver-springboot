package com.example.chaterserver.mapper;

import com.example.chaterserver.entity.Friendcontact;
import com.example.chaterserver.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Repository
public interface FriendMapper {
    int insert();
    ArrayList<User> find(User user);
    ArrayList<Friendcontact> selectContact(User user);
    int delete(Friendcontact friendcontact);

}
