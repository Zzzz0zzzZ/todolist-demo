package com.todolistbackend.quartz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.todolistbackend.entity.Todo;
import com.todolistbackend.entity.User;
import com.todolistbackend.mapper.TodoMapper;
import com.todolistbackend.mapper.UserMapper;
import com.todolistbackend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public class EmailTask{

    @Autowired
    private MailService mailService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TodoMapper todoMapper;

    @Scheduled(cron = "0 0 10,20 ? * *")
//    @Scheduled(cron = "*/10 * * * * ?")
    public void notification() {
        System.out.println("开始发送邮件");

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("notification", "1").isNotNull("email");
        List<User> users = userMapper.selectList(queryWrapper);

        users.forEach(user -> {

            QueryWrapper<Todo> todoQueryWrapper = new QueryWrapper<>();
            todoQueryWrapper.eq("userid", user.getUserid());
            List<Todo> todos = todoMapper.selectList(todoQueryWrapper);

            // 遍历一个用户所有todo
            StringBuilder stringBuilder = new StringBuilder();
            todos.forEach(todo -> {
                stringBuilder
                        .append("【待办】 ")
                        .append(todo.getContent())
                        .append("【截止】 ")
                        .append(todo.getDeadline())
                        .append(System.getProperty("line.separator"));
            });

            // 发邮件: Content用拼接好的字符串
            try {
                mailService.sendSimpleMail(
                        user.getEmail(),
                        "【todolist】待办提醒",
                        stringBuilder.toString()
                );
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        System.out.println("发送定时邮件成功");
    }
}