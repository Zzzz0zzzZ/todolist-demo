package com.todolistbackend.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
<<<<<<< HEAD
import com.todolistbackend.entity.User;
import com.todolistbackend.mapper.TodoMapper;
import com.todolistbackend.entity.Todo;
import com.todolistbackend.mapper.UserMapper;
=======
import com.todolistbackend.mapper.TodoMapper;
import com.todolistbackend.pojo.Todo;
>>>>>>> 92346e44a1242d4e91f4a0938f50d67ff2487026
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*", "null"})
public class TodoController {
    @Autowired
    private TodoMapper todoMapper;

<<<<<<< HEAD

    @GetMapping("/todos/{userid}")
    public String getTodos(@PathVariable Integer userid) {
        QueryWrapper<Todo> qw = new QueryWrapper<>();
        qw.eq("userid",userid);
        List<Todo> todos = todoMapper.selectList(qw);
=======
    @GetMapping("/todos")
    public String getTodos() {
        List<Todo> todos = todoMapper.selectList(null);
>>>>>>> 92346e44a1242d4e91f4a0938f50d67ff2487026
        return JSON.toJSONString(todos);
    }

    @PostMapping("/delete")
    public void delTodo(@RequestBody Todo todo) {
        todoMapper.deleteById(todo);
    }

<<<<<<< HEAD
    @GetMapping("/count_total/{userid}")
    public long countTodos(@PathVariable Integer userid) {
        QueryWrapper<Todo> wrapper = new QueryWrapper<>();
        wrapper.eq("userid",userid);
        return todoMapper.selectCount(wrapper);
    }

    @GetMapping("/count_finish/{userid}")
    public long countFinishTodos(@PathVariable Integer userid) {
        QueryWrapper<Todo> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1).eq("userid",userid);
=======
    @GetMapping("/count_total")
    public long countTodos() {
        QueryWrapper<Todo> wrapper = new QueryWrapper<>();
        return todoMapper.selectCount(wrapper);
    }

    @GetMapping("/count_finish")
    public long countFinishTodos() {
        QueryWrapper<Todo> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
>>>>>>> 92346e44a1242d4e91f4a0938f50d67ff2487026
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