package com.todolistbackend.service;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpSession;

public interface UserService {
    JSONObject login(String username, String password, Integer error_submit, String verifycode, HttpSession httpSession);

    String register(String username, String password, String verifycode, HttpSession httpSession);

    Boolean change_password(String username, String password, String new_password);

    Boolean change_username(String username, String new_username);

    String setSettings(Integer userid, String notification);

    String getSettings(Integer userid);

    String getESettings(Integer userid);

    String setEmails(Integer userid, String notification, String email);
}
