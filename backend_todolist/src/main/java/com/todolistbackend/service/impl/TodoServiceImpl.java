package com.todolistbackend.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.todolistbackend.entity.Todo;
import com.todolistbackend.mapper.TodoMapper;
import com.todolistbackend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoMapper todoMapper;

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

    @Override
    public String getTodos(Integer userid) {
        QueryWrapper<Todo> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userid);
        List<Todo> todos = todoMapper.selectList(wrapper);
        return JSON.toJSONString(todos);
    }

    @Override
    public void delTodo(Todo todo) {
        todoMapper.deleteById(todo);
    }

    @Override
    public void delAllTodo(Integer userid) {
        QueryWrapper<Todo> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userid);
        todoMapper.delete(wrapper);
    }

    @Override
    public long countTodos(Integer userid) {
        QueryWrapper<Todo> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userid);
        return culTodayTodo(wrapper);
    }

    @Override
    public long countFinishTodos(Integer userid) {
        QueryWrapper<Todo> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1).eq("userid", userid);
        return culTodayTodo(wrapper);
    }

    @Override
    public void addTodo(Todo todo) {
        todoMapper.insert(todo);
    }

    @Override
    public void updateTodo(Todo todo) {
        todoMapper.updateById(todo);
    }
}
