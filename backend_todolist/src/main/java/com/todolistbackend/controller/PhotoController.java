package com.todolistbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.todolistbackend.entity.Photo;
import com.todolistbackend.mapper.PhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
public class PhotoController {
    @Autowired
    private PhotoMapper photoMapper;

    @Resource
    private RedisTemplate<String, byte[]> redisTemplate;

    @PostMapping("/setphoto")
    public void setPhoto(MultipartFile file, Integer userid) throws IOException {
        byte[] bytes = file.getBytes();
        UpdateWrapper<Photo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userid", userid).set("data", bytes);
        photoMapper.update(new Photo(), updateWrapper);

        // Redis 失效时间设为3天
        String key = "todo_photo_userid_" + userid;
        redisTemplate.opsForValue().set(key, bytes, 3, TimeUnit.DAYS);
    }

    @GetMapping("/getphoto/{userid}")
    public byte[] getPhoto(@PathVariable Integer userid) {
        String key = "todo_photo_userid_" + userid;
        // Redis 中找到直接返回
        byte[] redis_data = redisTemplate.opsForValue().get(key);
        if (redis_data != null) {
            return redis_data;
        }

        QueryWrapper<Photo> photoQueryWrapper = new QueryWrapper<>();
        photoQueryWrapper.eq("userid", userid);
        Photo photo = photoMapper.selectOne(photoQueryWrapper);
        byte[] mysql_data = photo.getData();
        redisTemplate.opsForValue().set(key, mysql_data, 3, TimeUnit.DAYS);
        return mysql_data;
    }
}
