package com.bling.dab.common.model;

import com.bling.dab.domain.SysRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @author: hxp
 * @date: 2019/6/3 17:11
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoReq {

    private Integer uid;
    private String username;//帐号
    private String name;//名称（昵称或者真实姓名，不同系统不同定义）
    private String password; //密码;
    private String salt;//加密密码的盐
    private byte state;//用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.


    private String currentPage;
    private String pageSize;
    private String order;
    private Set<Integer> sets;
}