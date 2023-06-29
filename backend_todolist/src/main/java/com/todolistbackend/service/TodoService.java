package com.todolistbackend.service;

import com.todolistbackend.entity.Todo;

public interface TodoService {
    String getTodos(Integer userid);

    void delTodo(Todo todo);

    void delAllTodo(Integer userid);

    long countTodos(Integer userid);

    long countFinishTodos(Integer userid);

    void addTodo(Todo todo);

    void updateTodo(Todo todo);
}
