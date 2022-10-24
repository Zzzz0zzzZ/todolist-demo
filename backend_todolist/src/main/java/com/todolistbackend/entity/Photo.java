package com.todolistbackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("profilephoto")
public class Photo {
    private Integer userid;
    private byte[] data;
}
