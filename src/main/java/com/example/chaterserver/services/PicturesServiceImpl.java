package com.example.chaterserver.services;

import com.example.chaterserver.entity.Picture;
import com.example.chaterserver.mapper.PictureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service("picturesService")
public class PicturesServiceImpl implements PicturesService{

    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public int addNewChatPicInfo(Picture picture) {
        return 0;
    }

    @Override
    public List<Picture> findPic(Picture picture) {
        return null;
    }
}
