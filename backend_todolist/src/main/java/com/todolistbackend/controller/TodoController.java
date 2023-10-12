package com.todolistbackend.controller;

import com.todolistbackend.entity.Todo;
import com.todolistbackend.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/todos/{userid}")
    public String getTodos(@PathVariable Integer userid) {
        return todoService.getTodos(userid);
    }

    @PostMapping("/delete")
    public void delTodo(@RequestBody Todo todo) {
        todoService.delTodo(todo);
    }

    @PostMapping("/delete-all")
    public void delAllTodo(@RequestBody Map<String, String> mp) {
        Integer userid = Integer.parseInt(mp.get("userid"));
        todoService.delAllTodo(userid);
    }

    @GetMapping("/count_total/{userid}")
    public long countTodos(@PathVariable Integer userid) {
        return todoService.countTodos(userid);
    }

    @GetMapping("/count_finish/{userid}")
    public long countFinishTodos(@PathVariable Integer userid) {
        return todoService.countFinishTodos(userid);
    }

    @PostMapping("/add")
    public void addTodo(@RequestBody Todo todo) {
        todoService.addTodo(todo);
    }

    @PostMapping("/update")
    public void updateTodo(@RequestBody Todo todo) {
        todoService.updateTodo(todo);
    }
}