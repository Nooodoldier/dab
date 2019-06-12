package com.bling.dab.service;

import com.bling.dab.common.model.SysPermissionReq;
import com.bling.dab.common.model.SysRoleReq;
import com.bling.dab.common.result.Result;
import com.bling.dab.dao.SysPermissionRepository;
import com.bling.dab.domain.SysPermission;
import com.bling.dab.domain.SysRole;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: hxp
 * @date: 2019/6/4 15:20
 * @description:权限管理
 */
@Service
public class SysPermissionService {

    @Autowired
    private SysPermissionRepository sysPermissionRepository;

    public Result saveSysPermission(SysPermission sysPermission){
        SysPermission permission = sysPermissionRepository.save(sysPermission);
        return Result.success(permission);
    }


    public Result findAll(SysPermissionReq sysPermissionReq) {
        int page = NumberUtils.toInt(sysPermissionReq.getCurrentPage(), 0);
        int size = NumberUtils.toInt(sysPermissionReq.getPageSize(), 10);
        String order = sysPermissionReq.getOrder();
        SysPermission sysPermission = new SysPermission();
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, order);
        Example<SysPermission> example = Example.of(sysPermission);
        Page<SysPermission> infoPage = sysPermissionRepository.findAll(example, pageable);
        return Result.success(infoPage);
    }

    public Result savePermission(SysPermissionReq sysPermissionReq) {
        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(sysPermissionReq,sysPermission);
        SysPermission sysPermission1 = sysPermissionRepository.save(sysPermission);
        return Result.success(sysPermission1);
    }

    public Result deletePermission(SysPermissionReq sysPermissionReq) {
        Set<Integer> pids = sysPermissionReq.getSets();
        HashSet<SysPermission> permissions = new HashSet<>();
        pids.forEach((t)->{
            SysPermission sysPermission = sysPermissionRepository.getOne(t);
            permissions.add(sysPermission);
        });
        sysPermissionRepository.deleteInBatch(permissions);
        return Result.success();
    }
}
