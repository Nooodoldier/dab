package com.bling.dab.dao;

import com.bling.dab.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    /**
     * 通过uid查询
     * @param uid
     * @return
     */
    UserInfo findByUid(Integer uid);

    UserInfo getByUid(Integer uid);

    /**
     * user role 关联表保存
     * nativeQuery = true的作用是为true时,可以为原生的sql;为false时原生sql的表名对应为实体类
     * @param uid
     * @param roleId
     * @return
     */
    @Modifying
    @Query(value = "insert into sys_user_role(uid,role_id) values(?1,?2)",nativeQuery = true)
    int saveUserRole(Integer uid, Integer roleId);

    @Modifying
    @Query(value = "update user_info set name = ?1 where uid = ?2",nativeQuery = true)
    int updateUser(String name, Integer uid);
}
