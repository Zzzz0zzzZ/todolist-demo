package com.todolistbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.todolistbackend.entity.Photo;
import com.todolistbackend.mapper.PhotoMapper;
import com.todolistbackend.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    private PhotoMapper photoMapper;

    @Override
    public void setPhoto(MultipartFile file, Integer userid) throws IOException {
        byte[] bytes = file.getBytes();
        UpdateWrapper<Photo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userid", userid).set("data", bytes);
        photoMapper.update(new Photo(), updateWrapper);
    }

    @Override
    public byte[] getPhoto(Integer userid) {
        QueryWrapper<Photo> photoQueryWrapper = new QueryWrapper<>();
        photoQueryWrapper.eq("userid", userid);
        Photo photo = photoMapper.selectOne(photoQueryWrapper);
        return photo.getData();
    }
}
