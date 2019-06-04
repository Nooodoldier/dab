package com.bling.dab.dao;

import com.bling.dab.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: hxp
 * @date: 2019/6/3 15:46
 * @description:
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {


    /**
     * 通过用户名查找
     * @param username
     * @return
     */
    UserInfo findByUsername(String username);
}
