package com.bling.dab.service;

import com.bling.dab.dao.SysPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: hxp
 * @date: 2019/6/4 15:20
 * @description:权限管理
 */
@Service
public class SysPermissionService {

    @Autowired
    private SysPermissionRepository sysPermissionRepository;


}
