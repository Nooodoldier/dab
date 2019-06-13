package com.bling.dab.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: hxp
 * @date: 2019/6/3 17:11
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResp {

    private Integer uid;
    private String username;//帐号
    private String name;//名称（昵称或者真实姓名，不同系统不同定义）
    private String password; //密码;
    private String salt;//加密密码的盐
    private byte state;//用户状态
}
