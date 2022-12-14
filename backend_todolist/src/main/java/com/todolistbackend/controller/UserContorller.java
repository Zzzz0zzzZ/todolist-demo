package com.todolistbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.todolistbackend.entity.Photo;
import com.todolistbackend.entity.User;
import com.todolistbackend.mapper.PhotoMapper;
import com.todolistbackend.mapper.UserMapper;
import com.todolistbackend.utils.TokenUtils;
import com.todolistbackend.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@RestController
public class UserContorller {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PhotoMapper photoMapper;

    @PostMapping("/login")
    public JSONObject login(@RequestBody Map<String, String> mp, HttpSession httpSession) {
        JSONObject res = new JSONObject();
        if (Integer.parseInt(mp.get("error_submit")) >= 3 && !VerifyCodeUtils.checkCode(mp.get("verifycode"), httpSession)) {
            res.put("message", "verifycode_error");
        } else {
            QueryWrapper<User> qw = new QueryWrapper<>();
            qw.eq("password", mp.get("password")).eq("username", mp.get("username"));
            User us = userMapper.selectOne(qw);

            if (us != null) {
                res.put("username", us.getUsername());
                res.put("userid", us.getUserid());
                res.put("token", TokenUtils.createToken(us));
                res.put("message", "success");
            } else {
                res.put("message", "user_error");
            }
        }
        return res;
    }

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> mp, HttpSession httpSession) {
        if (!VerifyCodeUtils.checkCode(mp.get("verifycode"), httpSession)) {
            return "verifycode_error";
        }

        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", mp.get("username"));
        User us = userMapper.selectOne(qw);
        if (us != null) {
            return "username_error";
        } else {
            // ?????????????????????????????? userid
            QueryWrapper<User> maxid = new QueryWrapper<>();
            maxid.select("max(userid) as userid");
            // ??? userinfo ???????????????
            Integer userid = userMapper.selectOne(maxid).getUserid() + 1;
            User user = new User();
            user.setUserid(userid);
            user.setUsername(mp.get("username"));
            user.setPassword(mp.get("password"));
            userMapper.insert(user);

            // ??? profilephoto ???????????????
            Photo photo = new Photo();
            photo.setUserid(userid);
            photoMapper.insert(photo);

            return "success";
        }
    }

    @PostMapping("/change_password")
    public Boolean change_password(@RequestBody Map<String, String> mp, @RequestHeader Map<String, String> head) {
        if (TokenUtils.checkReq(head.get("token"), Integer.parseInt(mp.get("userid")))) {
            QueryWrapper<User> us = new QueryWrapper<>();
            us.eq("username", mp.get("username")).eq("password", mp.get("password"));
            User user = userMapper.selectOne(us);
            if (user != null) {
                UpdateWrapper<User> update_user = new UpdateWrapper<>();
                update_user.eq("username", mp.get("username")).set("password", mp.get("new_password"));
                userMapper.update(new User(), update_user);
                return true;
            }
        }
        return false;
    }

    @PostMapping("/change_username")
    public Boolean change_username(@RequestBody Map<String, String> mp, @RequestHeader Map<String, String> head) {
        if (TokenUtils.checkReq(head.get("token"), Integer.parseInt(mp.get("userid")))) {
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
        return false;
    }

    @GetMapping("/getCode")
    public void getCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        VerifyCodeUtils.createCode(httpServletRequest, httpServletResponse);
    }


    @PostMapping("/checkToken")
    public Boolean checkToken(@RequestBody Map<String, String> mp) {
        return TokenUtils.checkToken(mp.get("token"));
    }
}
