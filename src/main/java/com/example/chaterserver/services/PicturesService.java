package com.example.chaterserver.services;

import com.example.chaterserver.entity.Picture;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PicturesService {



    int addNewChatPicInfo(Picture picture);


    /**@param picture 传入参数PicInfo
     *
     * picture中有设置的参数即可作为筛选条件，置空则不作为条件*/
    List<Picture> findPic(Picture picture);



}
