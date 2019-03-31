package com.bling.dab.domain;

import lombok.*;

import java.math.BigDecimal;

/**
 * @author: hxp
 * @date: 2019/3/31 11:58
 * @description:
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private int cid;

    private int uid;

    private String carName;

    private BigDecimal carPrice;
}
