package com.bling.dab.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;
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
@Table(name = "t_user")
public class User {

    private int id;

    private String name;

    private int age;

}
