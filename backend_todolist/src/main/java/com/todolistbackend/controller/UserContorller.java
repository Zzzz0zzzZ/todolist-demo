package com.todolistbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.todolistbackend.service.UserService;
import com.todolistbackend.utils.TokenUtils;
import com.todolistbackend.utils.VerifyCodeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserContorller {
    private final UserService userService;

    @PostMapping("/login")
    public JSONObject login(@RequestBody Map<String, String> mp, HttpSession httpSession) {
        String username = mp.get("username");
        String password = mp.get("password");
        Integer error_submit = Integer.valueOf(mp.get("error_submit"));
        String verifycode = mp.get("verifycode");
        return userService.login(username, password, error_submit, verifycode, httpSession);
    }

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> mp, HttpSession httpSession) {
        String username = mp.get("username");
        String password = mp.get("password");
        String verifycode = mp.get("verifycode");
        return userService.register(username, password, verifycode, httpSession);
    }

    @PostMapping("/change_password")
    public Boolean change_password(@RequestBody Map<String, String> mp) {
        String username = mp.get("username");
        String password = mp.get("password");
        String new_password = mp.get("new_password");
        return userService.change_password(username, password, new_password);
    }

    @PostMapping("/change_username")
    public Boolean change_username(@RequestBody Map<String, String> mp) {
        String username = mp.get("username");
        String new_username = mp.get("new_username");
        return userService.change_username(username, new_username);
    }

    @GetMapping("/getCode")
    public void getCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        VerifyCodeUtils.createCode(httpServletRequest, httpServletResponse);
    }


    @PostMapping("/checkToken")
    public Boolean checkToken(@RequestBody Map<String, String> mp) {
        return TokenUtils.checkToken(mp.get("token"));
    }

    @PostMapping("/settings")
    public String setSettings(@RequestBody Map<String, String> mp) {
        Integer userid = Integer.valueOf(mp.get("userid"));
        String notification = mp.get("notification");
        return userService.setSettings(userid, notification);
    }

    @GetMapping("/settings/{userid}")
    public String getSettings(@PathVariable Integer userid) {
        log.info("查询了用户设置");
        return userService.getSettings(userid);
    }

    @GetMapping("/eSettings/{userid}")
    public String getESettings(@PathVariable Integer userid) {
        log.info("查询了用户设置");
        return userService.getESettings(userid);
    }

    @PostMapping("/setEmails")
    public String setEmails(@RequestBody Map<String, String> mp) {
        Integer userid = Integer.valueOf(mp.get("userid"));
        String notification = mp.get("notification");
        String email = mp.get("email");
        return userService.setEmails(userid, notification, email);
    }
}
