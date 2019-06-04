package com.bling.dab.service;

import com.bling.dab.common.result.Result;
import com.bling.dab.dao.LoginUserMapper;
import com.bling.dab.domain.LoginUser;
import com.bling.dab.domain.LoginUserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: hxp
 * @date: 2019/6/3 20:03
 * @description:
 */
@Service
public class LoginService {

    @Autowired
    private LoginUserMapper loginUserMapper;

    /**
     * 分页查询在线用户
     * @return
     */
    public Result getPageLoginUser(){
        PageHelper.startPage(0,10);
        LoginUserExample loginUserExample= new LoginUserExample();
        List<LoginUser> loginUsers = loginUserMapper.selectByExample(loginUserExample);
        PageInfo<LoginUser> loginUserPageInfo = new PageInfo<>(loginUsers);
        return Result.success(loginUserPageInfo);
    }
}
