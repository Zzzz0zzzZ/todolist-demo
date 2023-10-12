package com.todolistbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.todolistbackend.entity.Photo;
import com.todolistbackend.mapper.PhotoMapper;
import com.todolistbackend.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final PhotoMapper photoMapper;
    private final String photoRedisBaseKey = "todo_photo_";
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setPhoto(MultipartFile file, Integer userid) throws IOException {
        byte[] bytes = file.getBytes();
        UpdateWrapper<Photo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userid", userid).set("data", bytes);
        photoMapper.update(new Photo(), updateWrapper);
        redisTemplate.opsForValue().set(photoRedisBaseKey + userid, bytes, 3, TimeUnit.DAYS);
    }

    @Override
    public byte[] getPhoto(Integer userid) {
        String photoRedisKey = photoRedisBaseKey + userid;
        // 从redis获取photo
        byte[] photo = (byte[]) redisTemplate.opsForValue().get(photoRedisKey);
        // 判断是否在redis
        if (photo != null) {
            return photo;
        }
        // 设置过期时间
        QueryWrapper<Photo> photoQueryWrapper = new QueryWrapper<>();
        photoQueryWrapper.eq("userid", userid);
        photo = photoMapper.selectOne(photoQueryWrapper).getData();
        redisTemplate.opsForValue().set(photoRedisKey, photo, 3, TimeUnit.DAYS);
        return photo;
    }
}
