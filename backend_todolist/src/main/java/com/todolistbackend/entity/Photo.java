package com.todolistbackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("profilephoto")
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
    private Integer userid;
    private byte[] data;
}
