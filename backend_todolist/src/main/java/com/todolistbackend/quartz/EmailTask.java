package com.todolistbackend.quartz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.todolistbackend.entity.Todo;
import com.todolistbackend.entity.User;
import com.todolistbackend.mapper.TodoMapper;
import com.todolistbackend.mapper.UserMapper;
import com.todolistbackend.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class EmailTask {
    private final MailService mailService;

    private final UserMapper userMapper;

    private final TodoMapper todoMapper;

    @Scheduled(cron = "0 0 10,13,19 ? * *")
//    @Scheduled(cron = "*/10 * * * * ?")
    public void notification() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("notification", "1").isNotNull("email");
        List<User> users = userMapper.selectList(queryWrapper);
        log.info(users.toString());
        users.forEach(user -> {

            QueryWrapper<Todo> todoQueryWrapper = new QueryWrapper<>();
            todoQueryWrapper
                    .eq("userid", user.getUserid())
                    .eq("status", 0);
            List<Todo> todos = todoMapper.selectList(todoQueryWrapper);

            if (!todos.isEmpty()) {
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
                    log.info("邮件推送提醒成功, 用户为: " + user.getUsername() + "邮箱为: " + user.getEmail());
                } catch (Exception e) {
                    log.warn("邮件推送提醒失败, 用户为: " + user.getUsername() + "邮箱为: " + user.getEmail());
                }
            }
        });
    }
}