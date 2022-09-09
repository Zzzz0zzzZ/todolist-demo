package com.todolistbackend.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.todolistbackend.entity.User;
import com.todolistbackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"*", "null"})
public class UserContorller {
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("password", user.getPassword());
        qw.eq("username", user.getUsername());
        User us = userMapper.selectOne(qw);
        return JSON.toJSONString(us);
    }

    @PostMapping("/register")
    public Boolean register(@RequestBody User user) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", user.getUsername());
        User us = userMapper.selectOne(qw);
        if (us != null) {
            return false;
        } else {
            QueryWrapper<User> maxid = new QueryWrapper<>();
            maxid.select("max(userid) as userid");
            User ur = userMapper.selectOne(maxid);
            user.setUserid(ur.getUserid()+1);
            userMapper.insert(user);
            return true;
        }
    }
}
