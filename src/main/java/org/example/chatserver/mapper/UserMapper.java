package org.example.chatserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.chatserver.dto.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
