package com.bling.dab.service;

import com.bling.dab.common.model.SysRoleReq;
import com.bling.dab.common.result.Result;
import com.bling.dab.dao.SysRoleRepository;
import com.bling.dab.domain.SysRole;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        sysRole.setAvailable(sysRoleReq.getAvailable());
        sysRole.setDescription(sysRoleReq.getDescription());
        sysRole.setRole(sysRoleReq.getRole());
        SysRole save = sysRoleRepository.save(sysRole);
        return Result.success(save);
    }
    public Result saveBatchRole(List<SysRoleReq> list){
        ArrayList<SysRole> list1 = Lists.newArrayList();
        list.forEach((t)->{
            SysRole sysRole = new SysRole();
            sysRole.setAvailable(t.getAvailable());
            sysRole.setDescription(t.getDescription());
            sysRole.setRole(t.getRole());
            list1.add(sysRole);
        });
        List<SysRole> roles = sysRoleRepository.saveAll(list1);
        return Result.success(roles);
    }


    
}
