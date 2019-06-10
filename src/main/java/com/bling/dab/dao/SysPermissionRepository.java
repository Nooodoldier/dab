package com.bling.dab.dao;


import com.bling.dab.domain.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: hxp
 * @date: 2019/6/3 15:51
 * @description:
 */
@Repository
public interface SysPermissionRepository extends JpaRepository<SysPermission,Integer> {

}