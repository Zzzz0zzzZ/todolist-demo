## ToDoList

### 项目地址

[To-Do List](http://152.136.154.181/todo)

### 项目功能

##### 已完成

1. [event_list] 记录待办事项，实现对待办事项的增改删查动能
2. [today_card] 实时获取城市天气信息
3. [calendar] 日历功能

##### 待优化

1. [today_card] “进度”视图的动态更新

   **bug**：无法获得动态更新后的值，只要页面不手动刷新，就一直是初始化网页时从后端请求到的值。

   当前功能：目前只能手动刷新实现数据更新。

   代码逻辑：对帖子进行操作时调用`pinia`中actions方法更新`state`中变量的值; 在today_card中调用`pinia`中`getters`方法获取更新后的值。

##### 待完成

1. 登陆注册功能，不同用户展示不同待办事项

2. ......

### 项目构建

##### 前端

Vue3 + Bootstrap + Element Plus 

##### 后端

Springboot + myBatis Plus + mysql

### 项目说明

#### 关于数据库

- 出于安全性考虑，后端连接数据库代码部分未开源

- 配置文件在`ToDoList-backend\src\main\resources\application.yml`
  - 使用时请自行配置数据库信息 `{url, username, password}`
  - 在端口正常的情况下即可直接使用

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: 
    username:
    password: 
  main:
    banner-mode: off

server:
  port: 8060

mybatis-plus:
  global-config:
    banner: false
```

- 数据库部分建表代码如下

```mysql
CREATE TABLE `todolist` (
`id` int NOT NULL AUTO_INCREMENT,
`userid` INT NOT NULL ,
`content` varchar(1000) NOT NULL,
`create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
`update_time` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
`status` INT DEFAULT 0,
PRIMARY KEY (`id`)
) ENGINE = InnoDB;
```
