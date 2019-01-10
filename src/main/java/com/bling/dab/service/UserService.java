package com.bling.dab.service;

import com.bling.dab.dao.UserMapper;
import com.bling.dab.domain.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: hxp
 * @date: 2019/1/10 19:07
 * @description:
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public int saveUser(User user){
        return userMapper.saveUser(user);
    }
}
