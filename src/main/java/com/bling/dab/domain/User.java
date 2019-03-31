package com.bling.dab.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author: hxp
 * @date: 2019/1/10 16:42
 * @description:
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;

    private String name;

    private int age;

}
