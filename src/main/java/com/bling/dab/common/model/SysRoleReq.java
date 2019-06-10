package com.bling.dab.common.model;

import com.bling.dab.domain.SysPermission;
import com.bling.dab.domain.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: hxp
 * @date: 2019/6/4 16:00
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleReq {

    private Integer id; // 编号
    private String role; // 角色标识程序中判断使用,如"admin",这个是唯一的:
    private String description; // 角色描述,UI界面显示使用
    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户

    //角色 -- 权限关系：多对多关系;
    private List<SysPermission> permissions;

    // 用户 - 角色关系定义;
    private List<UserInfo> userInfos;// 一个角色对应多个用户
}