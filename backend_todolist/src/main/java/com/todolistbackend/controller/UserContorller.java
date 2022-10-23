package com.todolistbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.todolistbackend.entity.User;
import com.todolistbackend.mapper.UserMapper;
import com.todolistbackend.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserContorller {
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public JSONObject login(@RequestBody User user) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("password", user.getPassword()).eq("username", user.getUsername());
        User us = userMapper.selectOne(qw);
        JSONObject res = new JSONObject();
        if (us != null) {
            res.put("username", us.getUsername());
            res.put("userid", us.getUserid());
            res.put("token", TokenUtils.createToken(us));
            return res;
        }
        return res;
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
            user.setUserid(ur.getUserid() + 1);
            userMapper.insert(user);
            return true;
        }
    }

    @PostMapping("/change_password")
    public Boolean change_password(@RequestBody Map<String, String> mp) {
        QueryWrapper<User> us = new QueryWrapper<>();
        us.eq("username", mp.get("username")).eq("password", mp.get("password"));
        User user = userMapper.selectOne(us);
        if (user != null) {
            UpdateWrapper<User> update_user = new UpdateWrapper<>();
            update_user.eq("username", mp.get("username")).set("password", mp.get("new_password"));
            userMapper.update(new User(), update_user);
            return true;
        }
        return false;
    }

    @PostMapping("/change_username")
    public Boolean change_username(@RequestBody Map<String, String> mp) {
        QueryWrapper<User> us = new QueryWrapper<>();
        us.eq("username", mp.get("new_username"));
        User user = userMapper.selectOne(us);
        if (user != null) {
            return false;
        }
        UpdateWrapper<User> update_user = new UpdateWrapper<>();
        update_user.eq("username", mp.get("username")).set("username", mp.get("new_username"));
        userMapper.update(new User(), update_user);
        return true;
    }

    @PostMapping("/checkToken")
    public Boolean checkToken(@RequestBody Map<String, String> mp) {
        return TokenUtils.checkToken(mp.get("token"));
    }
}
