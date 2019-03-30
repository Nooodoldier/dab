package com.bling.dab.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: hxp
 * @date: 2019/3/28 19:06
 * @description:
 */
@Data
public class BatchData {

    private int id;

    private String name;

    private int age;

    private int cid;

    private String carName;

    private BigDecimal carPrice;

}
