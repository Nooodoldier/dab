package com.bling.dab.domain;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author: hxp
 * @date: 2019/1/10 16:42
 * @description:
 */
public class User {

    private int id;

    private String name;

    private int age;

    private Date ctm;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getCtm() {
        return ctm;
    }

    public void setCtm(Date ctm) {
        this.ctm = ctm;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", ctm=" + ctm +
                '}';
    }
}
