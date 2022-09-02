package com.todolistbackend.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.todolistbackend.mapper.TodoMapper;
import com.todolistbackend.pojo.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*", "null"})
public class TodoController {
    @Autowired
    private TodoMapper todoMapper;

    @GetMapping("/todos")
    public String getTodos() {
        List<Todo> todos = todoMapper.selectList(null);
        return JSON.toJSONString(todos);
    }

    @PostMapping("/delete")
    public void delTodo(@RequestBody Todo todo) {
        todoMapper.deleteById(todo);
    }

    @GetMapping("/count_total")
    public long countTodos() {
        QueryWrapper<Todo> wrapper = new QueryWrapper<>();
        return todoMapper.selectCount(wrapper);
    }

    @GetMapping("/count_finish")
    public long countFinishTodos() {
        QueryWrapper<Todo> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        return todoMapper.selectCount(wrapper);
    }

    @PostMapping("/add")
    public void addTodo(@RequestBody Todo todo) {
        todoMapper.insert(todo);
    }

    @PostMapping("/update")
    public void updateTodo(@RequestBody Todo todo) {
        todoMapper.updateById(todo);
    }
}