package com.todolistbackend.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.todolistbackend.entity.Photo;
import com.todolistbackend.entity.User;
import com.todolistbackend.mapper.PhotoMapper;
import com.todolistbackend.mapper.UserMapper;
import com.todolistbackend.service.UserService;
import com.todolistbackend.utils.TokenUtils;
import com.todolistbackend.utils.VerifyCodeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    private final PhotoMapper photoMapper;

    @Override
    public JSONObject login(String username, String password, Integer error_submit, String verifycode, HttpSession httpSession) {
        JSONObject res = new JSONObject();
        if (error_submit >= 3 && !VerifyCodeUtils.checkCode(verifycode, httpSession)) {
            res.put("message", "verifycode_error");
            return res;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("password", password).eq("username", username);
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            res.put("message", "user_error");
            return res;
        }

        res.put("username", user.getUsername());
        res.put("userid", user.getUserid());
        res.put("token", TokenUtils.createToken(user));
        res.put("message", "success");
        return res;
    }

    @Override
    public String register(String username, String password, String verifycode, HttpSession httpSession) {
        if (!VerifyCodeUtils.checkCode(verifycode, httpSession)) {
            return "verifycode_error";
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User us = userMapper.selectOne(queryWrapper);
        if (us != null) {
            return "username_error";
        }
        // 根据递增算出新用户的 userid
        QueryWrapper<User> maxid = new QueryWrapper<>();
        maxid.select("max(userid) as userid");
        // 表 userinfo 中插入数据
        Integer userid = userMapper.selectOne(maxid).getUserid() + 1;
        User user = new User(null, userid, username, password, null, null);
        userMapper.insert(user);

        // 表 profilephoto 中插入数据
        Photo photo = new Photo(userid, null);
        photoMapper.insert(photo);
        return "success";
    }

    @Override
    public Boolean change_password(String username, String password, String new_password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).eq("password", password);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return false;
        }
        UpdateWrapper<User> update_user = new UpdateWrapper<>();
        update_user.eq("username", username).set("password", new_password);
        userMapper.update(new User(), update_user);
        return true;
    }

    @Override
    public Boolean change_username(String username, String new_username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", new_username);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            return false;
        }
        UpdateWrapper<User> update_user = new UpdateWrapper<>();
        update_user.eq("username", username).set("username", new_username);
        userMapper.update(new User(), update_user);
        return true;
    }

    @Override
    public String setSettings(Integer userid, String notification) {
        // 查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        User user = userMapper.selectOne(queryWrapper);
        // 更新字段
        user.setNotification(notification);
        int res = userMapper.updateById(user);
        // 成功判断
        if (res == 1) return "success";
        else return "error";
    }

    @Override
    public String getSettings(Integer userid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        return userMapper.selectOne(queryWrapper).getNotification();
    }

    @Override
    public String getESettings(Integer userid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        return userMapper.selectOne(queryWrapper).getEmail();
    }

    @Override
    public String setEmails(Integer userid, String notification, String email) {
        // 查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        User user = userMapper.selectOne(queryWrapper);
        // 更新字段
        user.setNotification(notification);
        user.setEmail(email);
        // 成功判断返回 success
        return userMapper.updateById(user) == 1 ? "success" : "error";
    }
}
