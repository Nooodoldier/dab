package com.bling.dab.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bling.dab.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: hxp
 * @date: 2019/1/10 17:02
 * @description:
 */
//@Mapper
public interface UserMapper extends BaseMapper<User> {

    int saveUser(User user);
}
