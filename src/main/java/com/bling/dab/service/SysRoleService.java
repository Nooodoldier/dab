package com.bling.dab.service;

import com.bling.dab.common.model.SysRoleReq;
import com.bling.dab.common.result.Result;
import com.bling.dab.dao.SysRoleRepository;
import com.bling.dab.domain.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: hxp
 * @date: 2019/6/4 15:19
 * @description:角色管理
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleRepository sysRoleRepository;

    public Result saveRole(SysRoleReq sysRoleReq){
        SysRole sysRole = new SysRole();
        sysRole.setAvailable(true);
        sysRole.setDescription(sysRoleReq.getDescription());
        sysRole.setRole(sysRoleReq.getRole());
        sysRoleRepository.save(sysRole);
        return Result.success();
    }

    
}
