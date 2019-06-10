package com.bling.dab.service;

import com.bling.dab.domain.User;
import com.bling.dab.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: hxp
 * @date: 2019/1/10 19:07
 * @description:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
public class UserService {

    @Resource
    private UserMapper userMapper;

    public int saveUser(User user){
        return userMapper.saveUser(user);
    }

}
