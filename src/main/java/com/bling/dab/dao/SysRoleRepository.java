package com.bling.dab.dao;

import com.bling.dab.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: hxp
 * @date: 2019/6/3 15:50
 * @description:
 */
@Repository
public interface SysRoleRepository extends JpaRepository<SysRole,Integer> {


}
