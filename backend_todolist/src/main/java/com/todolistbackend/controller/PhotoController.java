package com.todolistbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.todolistbackend.entity.Photo;
import com.todolistbackend.mapper.PhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class PhotoController {
    @Autowired
    private PhotoMapper photoMapper;

    @PostMapping("/setphoto")
    public boolean setPhoto(MultipartFile file, Integer userid) throws IOException {
        byte[] bytes = file.getBytes();
        UpdateWrapper<Photo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userid", userid).set("data", bytes);
        photoMapper.update(new Photo(), updateWrapper);
        return true;
    }

    @GetMapping("/getphoto/{userid}")
    public byte[] getPhoto(@PathVariable Integer userid) {
        QueryWrapper<Photo> photoQueryWrapper = new QueryWrapper<>();
        photoQueryWrapper.eq("userid", userid);
        Photo photo = photoMapper.selectOne(photoQueryWrapper);
        return photo.getData();
    }
}
