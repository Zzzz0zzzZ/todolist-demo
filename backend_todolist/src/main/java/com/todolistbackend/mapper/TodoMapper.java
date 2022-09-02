package com.todolistbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.todolistbackend.pojo.Todo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodoMapper extends BaseMapper<Todo> {
}
