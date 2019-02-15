package com.bling.dab.dao;

import com.bling.dab.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author: hxp
 * @date: 2019/2/15 16:02
 * @description:
 */
public interface UserRepository extends MongoRepository<User,Integer> {


    /**
     * 自定义通过name查询
     * @param name
     * @return
     */
    List<User> findbyName(String name);
}
