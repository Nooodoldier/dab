package com.bling.dab.service;

import com.bling.dab.common.result.Result;
import com.bling.dab.mapper.LoginUserMapper;
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
public class LoginService{


    @Autowired
    private LoginUserMapper loginUserMapper;

    /**
     * 保存登陆用户
     * @param record
     * @return
     */
    public Result saveLoginUser(LoginUser record){
        int insert = loginUserMapper.insert(record);
        return Result.success(insert);
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public Result selectByPrimaryKey(Integer id){
        LoginUser user = loginUserMapper.selectByPrimaryKey(id);
        return Result.success(user);
    }

    /**
     * 根据ID查询
     * @param username
     * @return
     */
    public LoginUser selectLoginUserByUsername(String username){
        LoginUserExample example = new LoginUserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<LoginUser> userList = loginUserMapper.selectByExample(example);
        return userList.get(0);
    }

    /**
     * 分页查询
     * @param pageNum
     * @return
     */
    public Result selectByExample(int pageNum ,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        LoginUserExample example = new LoginUserExample();
        example.createCriteria().andIdBetween(0, 10);
        List<LoginUser> users = loginUserMapper.selectByExample(example);
        PageInfo<LoginUser> page = new PageInfo<>(users);
        return Result.success(page);
    }
}
