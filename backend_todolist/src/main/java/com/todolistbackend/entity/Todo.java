package com.todolistbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("todolist")
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userid;
    private String content;
    private String create_time;
    private String update_time;
    private Integer status;
    private String deadline;
}
