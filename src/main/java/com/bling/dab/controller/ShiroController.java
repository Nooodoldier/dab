package com.bling.dab.controller;

import com.bling.dab.common.annotation.LoginToken;
import com.bling.dab.common.model.*;
import com.bling.dab.common.result.Result;
import com.bling.dab.domain.SysPermission;
import com.bling.dab.domain.SysRole;
import com.bling.dab.domain.UserInfo;
import com.bling.dab.service.SysPermissionService;
import com.bling.dab.service.SysRoleService;
import com.bling.dab.service.UserInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * shiro管理
 */
@Slf4j
@Api(description = "shiro管理")
@RestController
@RequestMapping("/shiro")
public class ShiroController {


    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @LoginToken
    @ApiOperation("查询用户列表")
    @RequiresPermissions("userInfo:view")
    @PostMapping("/userInfo/userList")
    public Result getUserList(@RequestBody UserInfoReq userInfoReq){
        log.info("查询用户列表");
        int page = NumberUtils.toInt(userInfoReq.getCurrentPage(), 0);
        int size = NumberUtils.toInt(userInfoReq.getPageSize(), 10);
        PageHelper.startPage(page,size);
        List<UserInfo> userList = userInfoService.getUserList();
        ArrayList<UserInfoResp> list = Lists.newArrayList();
        Iterator<UserInfo> it = userList.iterator();
        while (it.hasNext()){
            UserInfoResp resp = new UserInfoResp();
            BeanUtils.copyProperties(it.next(),resp);
            list.add(resp);
        }
        PageInfo pageInfo = new PageInfo<>(list);
        return Result.success(pageInfo);
    }
    @LoginToken
    @ApiOperation("查询角色列表")
    @RequiresPermissions("sysRole:view")
    @PostMapping("/sysRole/roleList")
    public Result getRoleList(@RequestBody SysRoleReq sysRoleReq){
        log.info("查询角色列表");
        int page = NumberUtils.toInt(sysRoleReq.getCurrentPage(), 0);
        int size = NumberUtils.toInt(sysRoleReq.getPageSize(), 10);
        PageHelper.startPage(page,size);
        List<SysRole> roleList = sysRoleService.getRoleList();
        ArrayList<SysRoleResp> list = Lists.newArrayList();
        Iterator<SysRole> it = roleList.iterator();
        while (it.hasNext()){
            SysRoleResp resp = new SysRoleResp();
            BeanUtils.copyProperties(it.next(),resp);
            list.add(resp);
        }
        PageInfo pageInfo = new PageInfo<>(list);
        return Result.success(pageInfo);
    }
    @LoginToken
    @ApiOperation("查询权限列表")
    @RequiresPermissions("sysPermission:view")
    @PostMapping("/sysPermission/permissionList")
    public Result getPermissionList(@RequestBody SysPermissionReq sysPermissionReq){
        log.info("查询权限列表");
        int page = NumberUtils.toInt(sysPermissionReq.getCurrentPage(), 0);
        int size = NumberUtils.toInt(sysPermissionReq.getPageSize(), 10);
        PageHelper.startPage(page,size);
        List<SysPermission> permissionList = sysPermissionService.getPermissionList();
        ArrayList<SysPermissionResp> list = Lists.newArrayList();
        Iterator<SysPermission> it = permissionList.iterator();
        while (it.hasNext()){
            SysPermissionResp resp = new SysPermissionResp();
            BeanUtils.copyProperties(it.next(),resp);
            list.add(resp);
        }
        PageInfo pageInfo = new PageInfo<>(list);
        return Result.success(pageInfo);
    }
    @LoginToken
    @ApiOperation("用户添加")
    @RequiresPermissions("userInfo:add")
    @PostMapping("/userInfo/userAdd")
    public Result userAdd(@RequestBody UserInfoReq userInfoReq){
        UserInfo userInfo = userInfoService.saveUser(userInfoReq);
        if(userInfo == null){
            return Result.failure();
        }
        return Result.success(userInfo);
    }
    @LoginToken
    @ApiOperation("删除用户")
    @RequiresPermissions("userInfo:del")
    @PostMapping("/userInfo/userDel")
    public Result userDel(@RequestBody UserInfoReq userInfoReq){
        userInfoService.deleteUser(userInfoReq);
        return Result.success();
    }
    @LoginToken
    @ApiOperation("添加角色")
    @RequiresPermissions("sysRole:add")
    @PostMapping("/sysRole/roleAdd")
    public Result roleAdd(@RequestBody SysRoleReq sysRoleReq){
        SysRole role = sysRoleService.saveRole(sysRoleReq);
        return Result.success(role);
    }
    @LoginToken
    @ApiOperation("删除角色")
    @RequiresPermissions("sysRole:del")
    @PostMapping("/sysRole/roleDel")
    public Result roleDel(@RequestBody SysRoleReq sysRoleReq){
        sysRoleService.deleteRole(sysRoleReq);
        return Result.success();
    }
    @LoginToken
    @ApiOperation("添加权限")
    @RequiresPermissions("sysPermission:add")
    @PostMapping("/sysPermission/permissionAdd")
    public Result permissionAdd(@RequestBody SysPermissionReq sysPermissionReq){
        SysPermission sysPermission = sysPermissionService.savePermission(sysPermissionReq);
        return Result.success(sysPermission);
    }
    @LoginToken
    @ApiOperation("删除权限")
    @RequiresPermissions("sysPermission:del")
    @PostMapping("/sysPermission/permissionDel")
    public Result permissionDel(@RequestBody SysPermissionReq sysPermissionReq){
        sysPermissionService.deletePermission(sysPermissionReq);
        return Result.success();
    }
    @LoginToken
    @ApiOperation("修改角色权限")
    @RequiresPermissions("sysRole:updateRolePermission")
    @PostMapping("/sysRole/updateRolePermission")
    public Result updateRolePermission(@RequestBody SysRoleReq sysRoleReq){
        sysRoleService.updateRolePermission(sysRoleReq);
        return Result.success();
    }
    @LoginToken
    @ApiOperation("修改用户角色")
    @RequiresPermissions("userInfo:updateUserRole")
    @PostMapping("/userInfo/updateUserRole")
    public Result updateUserRole(@RequestBody UserInfoReq userInfoReq){
        userInfoService.updateUserRole(userInfoReq);
        return Result.success();
    }
}
