package com.todolistbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.todolistbackend.mapper")
public class ToDoListBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToDoListBackendApplication.class, args);
    }

}
