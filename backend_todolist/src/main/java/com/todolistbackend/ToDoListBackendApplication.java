package com.todolistbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling//开启定时
@MapperScan("com.todolistbackend.mapper")
@PropertySource("classpath:secrets.txt")
public class ToDoListBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToDoListBackendApplication.class, args);
    }

}
