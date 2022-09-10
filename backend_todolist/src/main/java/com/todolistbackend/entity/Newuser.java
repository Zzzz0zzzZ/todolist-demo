package com.todolistbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Newuser {
    @TableId(type = IdType.AUTO)
    private String username;
    private String password;
    private String new_password;
}
