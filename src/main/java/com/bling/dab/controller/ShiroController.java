package com.bling.dab.controller;

import com.bling.dab.common.model.SysPermissionReq;
import com.bling.dab.common.model.SysRoleReq;
import com.bling.dab.common.model.UserInfoReq;
import com.bling.dab.common.result.Result;
import com.bling.dab.service.SysPermissionService;
import com.bling.dab.service.SysRoleService;
import com.bling.dab.service.UserInfoService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * shiro管理
 */
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

    @PostMapping("/userInfo/userList")
    @RequiresPermissions("userInfo:view")
    public Result getUserList(UserInfoReq userInfoReq){
        Result result = userInfoService.findAll(userInfoReq);
        return result;
    }

    @PostMapping("/sysRole/roleList")
    @RequiresPermissions("sysRole:view")
    public Result getUserList(SysRoleReq sysRoleReq){
        Result result = sysRoleService.findAll(sysRoleReq);
        return result;
    }

    @PostMapping("/sysPermission/permissionList")
    @RequiresPermissions("sysPermission:view")
    public Result getUserList(SysPermissionReq sysPermissionReq){
        Result result = sysPermissionService.findAll(sysPermissionReq);
        return result;
    }

    @PostMapping("/userInfo/userAdd")
    @RequiresPermissions("userInfo:add")
    public Result userAdd(UserInfoReq userInfoReq){
        Result result = userInfoService.saveUser(userInfoReq);
        return result;
    }

    @PostMapping("/userInfo/userDel")
    @RequiresPermissions("userInfo:del")
    public Result userDel(UserInfoReq userInfoReq){
        Result result = userInfoService.deleteUser(userInfoReq);
        return result;
    }

    @PostMapping("/sysRole/roleAdd")
    @RequiresPermissions("sysRole:add")
    public Result roleAdd(SysRoleReq sysRoleReq){
        Result result = sysRoleService.saveRole(sysRoleReq);
        return result;
    }

    @PostMapping("/sysRole/roleDel")
    @RequiresPermissions("sysRole:del")
    public Result roleDel(SysRoleReq sysRoleReq){
        Result result = sysRoleService.deleteRole(sysRoleReq);
        return result;
    }
    @PostMapping("/sysPermission/permissionAdd")
    @RequiresPermissions("sysPermission:add")
    public Result permissionAdd(SysPermissionReq sysPermissionReq){
        Result result = sysPermissionService.savePermission(sysPermissionReq);
        return result;
    }

    @PostMapping("/sysPermission/permissionDel")
    @RequiresPermissions("sysPermission:del")
    public Result permissionDel(SysPermissionReq sysPermissionReq){
        Result result = sysPermissionService.deletePermission(sysPermissionReq);
        return result;
    }


    @PostMapping("/userInfo/updateUserRole")
    @RequiresPermissions("userInfo:updateUserRole")
    public Result permissionDel(UserInfoReq userInfoReq){
        Result result = userInfoService.updateUserRole(userInfoReq);
        return result;
    }

    @PostMapping("/sysRole/updateRolePermission")
    @RequiresPermissions("sysRole:updateRolePermission")
    public Result permissionDel(SysRoleReq sysRoleReq){
        Result result = sysRoleService.updateRolePermission(sysRoleReq);
        return result;
    }

}
