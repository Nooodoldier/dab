package com.bling.dab.domain;

import org.springframework.data.annotation.Id;

/**
 * @author: hxp
 * @date: 2019/1/10 16:42
 * @description:
 */
public class User {
    @Id
    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
