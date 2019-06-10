package com.bling.dab.dao;

import com.bling.dab.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author: hxp
 * @date: 2019/6/3 15:50
 * @description:
 */
@Repository
public interface SysRoleRepository extends JpaRepository<SysRole,Integer> {


    @Modifying
    @Query(value = "insert into sys_role_permission(permission_id,role_id) values(?1,?2)",nativeQuery = true)
    int saveRolePermission(Integer pid, Integer rid);
}
