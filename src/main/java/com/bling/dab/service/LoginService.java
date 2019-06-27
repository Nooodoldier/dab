package com.bling.dab.service;

import com.bling.dab.common.result.Result;
import com.bling.dab.mapper.LoginUserMapper;
import com.bling.dab.domain.LoginUser;
import com.bling.dab.domain.LoginUserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class ,propagation = Propagation.REQUIRED ,isolation = Isolation.READ_COMMITTED)
    public Result saveLoginUser(LoginUser record){
        int insert = loginUserMapper.insert(record);
        return Result.success(insert);
    }

    /**
     * 更新
     * @param record
     * @return
     */
    @CacheEvict(value="loginUser",allEntries=true)
    @Transactional(rollbackFor = Exception.class ,propagation = Propagation.REQUIRED ,isolation = Isolation.READ_COMMITTED)
    public int updateLoginUser(LoginUser record){
        return loginUserMapper.updateByPrimaryKey(record);
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Cacheable(value = "loginUser")
    public LoginUser selectByPrimaryKey(Integer id){
        return loginUserMapper.selectByPrimaryKey(id);
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
