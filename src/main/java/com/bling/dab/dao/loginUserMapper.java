package com.bling.dab.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bling.dab.domain.loginUser;
import com.bling.dab.domain.loginUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface loginUserMapper extends BaseMapper {
    int countByExample(loginUserExample example);

    int deleteByExample(loginUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(loginUser record);

    int insertSelective(loginUser record);

    List<loginUser> selectByExample(loginUserExample example);

    loginUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") loginUser record, @Param("example") loginUserExample example);

    int updateByExample(@Param("record") loginUser record, @Param("example") loginUserExample example);

    int updateByPrimaryKeySelective(loginUser record);

    int updateByPrimaryKey(loginUser record);
}