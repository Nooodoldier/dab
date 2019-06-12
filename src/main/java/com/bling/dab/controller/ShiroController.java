package com.bling.dab.controller;

import com.bling.dab.common.annotation.CheckToken;
import com.bling.dab.common.model.SysPermissionReq;
import com.bling.dab.common.model.SysRoleReq;
import com.bling.dab.common.model.UserInfoReq;
import com.bling.dab.common.result.Result;
import com.bling.dab.domain.UserInfo;
import com.bling.dab.service.SysPermissionService;
import com.bling.dab.service.SysRoleService;
import com.bling.dab.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * shiro管理
 */
@Slf4j
@Api(description = "shiro管理")
@RestController
//@RequestMapping("/shiro")
public class ShiroController {


    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SysRoleService sysRoleService;

    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token标记", required = true) })
    @CheckToken
    @PostMapping("/userInfo/userList")
    @RequiresPermissions("userInfo:view")
    public Result getUserList(@RequestBody UserInfoReq userInfoReq){
        log.info("token验证通过！");
        Page<UserInfo> all = userInfoService.findAll(userInfoReq);
        return Result.success(all);
    }
    @Autowired
    private SysPermissionService sysPermissionService;
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token标记", required = true) })
    @CheckToken
    @PostMapping("/sysRole/roleList")
    @RequiresPermissions("sysRole:view")
    public Result getUserList(@RequestBody SysRoleReq sysRoleReq){
        Result result = sysRoleService.findAll(sysRoleReq);
        return result;
    }
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token标记", required = true) })
    @CheckToken
    @PostMapping("/sysPermission/permissionList")
    @RequiresPermissions("sysPermission:view")
    public Result getUserList(@RequestBody SysPermissionReq sysPermissionReq){
        Result result = sysPermissionService.findAll(sysPermissionReq);
        return result;
    }
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token标记", required = true) })
    @CheckToken
    @PostMapping("/userInfo/userAdd")
    @RequiresPermissions("userInfo:add")
    public Result userAdd(@RequestBody UserInfoReq userInfoReq){
        Result result = userInfoService.saveUser(userInfoReq);
        return result;
    }
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token标记", required = true) })
    @CheckToken
    @PostMapping("/userInfo/userDel")
    @RequiresPermissions("userInfo:del")
    public Result userDel(@RequestBody UserInfoReq userInfoReq){
        Result result = userInfoService.deleteUser(userInfoReq);
        return result;
    }
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token标记", required = true) })
    @CheckToken
    @PostMapping("/sysRole/roleAdd")
    @RequiresPermissions("sysRole:add")
    public Result roleAdd(@RequestBody SysRoleReq sysRoleReq){
        Result result = sysRoleService.saveRole(sysRoleReq);
        return result;
    }
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token标记", required = true) })
    @CheckToken
    @PostMapping("/sysRole/roleDel")
    @RequiresPermissions("sysRole:del")
    public Result roleDel(@RequestBody SysRoleReq sysRoleReq){
        Result result = sysRoleService.deleteRole(sysRoleReq);
        return result;
    }
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token标记", required = true) })
    @CheckToken
    @PostMapping("/sysPermission/permissionAdd")
    @RequiresPermissions("sysPermission:add")
    public Result permissionAdd(@RequestBody SysPermissionReq sysPermissionReq){
        Result result = sysPermissionService.savePermission(sysPermissionReq);
        return result;
    }
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token标记", required = true) })
    @CheckToken
    @PostMapping("/sysPermission/permissionDel")
    @RequiresPermissions("sysPermission:del")
    public Result permissionDel(@RequestBody SysPermissionReq sysPermissionReq){
        Result result = sysPermissionService.deletePermission(sysPermissionReq);
        return result;
    }
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token标记", required = true) })
    @CheckToken
    @PostMapping("/userInfo/updateUserRole")
    @RequiresPermissions("userInfo:updateUserRole")
    public Result permissionDel(@RequestBody UserInfoReq userInfoReq){
        Result result = userInfoService.updateUserRole(userInfoReq);
        return result;
    }
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "token标记", required = true) })
    @CheckToken
    @PostMapping("/sysRole/updateRolePermission")
    @RequiresPermissions("sysRole:updateRolePermission")
    public Result permissionDel(@RequestBody SysRoleReq sysRoleReq){
        Result result = sysRoleService.updateRolePermission(sysRoleReq);
        return result;
    }

}
