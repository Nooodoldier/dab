package com.bling.dab.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: hxp
 * @date: 2019/5/17 15:06
 * @description:
 */
@Data
public class Girl implements Serializable {

    private String gid;

    private String gname;

    private String gemail;

}
