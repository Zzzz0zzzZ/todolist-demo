package com.todolistbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("userinfo")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userid;
    private String username;
    private String password;
}
