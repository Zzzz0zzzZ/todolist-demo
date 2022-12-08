package com.todolistbackend.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.todolistbackend.entity.Todo;
import com.todolistbackend.mapper.TodoMapper;
import com.todolistbackend.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class TodoController {
    @Autowired
    private TodoMapper todoMapper;

    @GetMapping("/todos/{userid}")
    public String getTodos(@PathVariable Integer userid, @RequestHeader Map<String, String> head) {
        List<Todo> todos = null;
        if (TokenUtils.checkReq(head.get("token"), userid)) {
            QueryWrapper<Todo> wrapper = new QueryWrapper<>();
            wrapper.eq("userid", userid);
            todos = todoMapper.selectList(wrapper);
        }
        return JSON.toJSONString(todos);
    }

    @PostMapping("/delete")
    public void delTodo(@RequestBody Todo todo, @RequestHeader Map<String, String> head) {
        if (TokenUtils.checkReq(head.get("token"), todo.getUserid())) {
            todoMapper.deleteById(todo);
        }
    }

    @GetMapping("/count_total/{userid}")
    public long countTodos(@PathVariable Integer userid, @RequestHeader Map<String, String> head) {
        if (TokenUtils.checkReq(head.get("token"), userid)) {
            QueryWrapper<Todo> wrapper = new QueryWrapper<>();
            wrapper.eq("userid", userid);
            return culTodayTodo(wrapper);
        }
        return 0;
    }

    @GetMapping("/count_finish/{userid}")
    public long countFinishTodos(@PathVariable Integer userid, @RequestHeader Map<String, String> head) {
        if (TokenUtils.checkReq(head.get("token"), userid)) {
            QueryWrapper<Todo> wrapper = new QueryWrapper<>();
            wrapper.eq("status", 1).eq("userid", userid);
            return culTodayTodo(wrapper);
        }
        return 0;
    }

    private long culTodayTodo(QueryWrapper<Todo> wrapper) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<Todo> todos = todoMapper.selectList(wrapper);
        long res = 0;
        for (Todo todo : todos) {
            if (todo.getDeadline() == null || todo.getDeadline().equals(df.format(date))) {
                res++;
            }
        }
        return res;
    }

    @PostMapping("/add")
    public void addTodo(@RequestBody Todo todo, @RequestHeader Map<String, String> head) {
        if (TokenUtils.checkReq(head.get("token"), todo.getUserid())) {
            todoMapper.insert(todo);
        }
    }

    @PostMapping("/update")
    public void updateTodo(@RequestBody Todo todo, @RequestHeader Map<String, String> head) {
        if (TokenUtils.checkReq(head.get("token"), todo.getUserid())) {
            todoMapper.updateById(todo);
        }
    }
}