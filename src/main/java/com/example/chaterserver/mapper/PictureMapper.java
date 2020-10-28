package com.example.chaterserver.mapper;

import com.example.chaterserver.entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PictureMapper {
    int insert(Picture picture);

    //pic中属性值若存在则作为筛选条件，为null则不作为条件
    List<Picture> query(Picture picture);
}
