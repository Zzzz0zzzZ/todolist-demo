package com.todolistbackend.controller;

import com.todolistbackend.service.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/setphoto")
    public void setPhoto(MultipartFile file, Integer userid, @RequestHeader Map<String, String> head) throws IOException {
        photoService.setPhoto(file, userid);
    }

    @GetMapping("/getphoto/{userid}")
    public byte[] getPhoto(@PathVariable Integer userid, @RequestHeader Map<String, String> head) {
        // 从redis获取photo
        byte[] hj = (byte[]) redisTemplate.opsForValue().get("hj");
        // 判断是否在redis
        if (hj != null) {
            log.warn("hj", hj);
            return hj;
        }
        byte[] photo = photoService.getPhoto(userid);
        // 设置过期时间
        redisTemplate.opsForValue().set("hj", photo, 3, TimeUnit.DAYS);
        return photo;
    }
}
