package com.bling.dab.domain;

import lombok.Data;

/**
 * @author: hxp
 * @date: 2019/4/24 20:02
 * @description:创建一个SessionData，用于保存在session中的字段
 */

@Data
public class SessionData {
    private Integer userCode;
    private String mobileNumber;
}
