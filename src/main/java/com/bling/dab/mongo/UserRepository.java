package com.bling.dab.mongo;

import com.bling.dab.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author: hxp
 * @date: 2019/2/15 16:02
 * @description:
 */
public interface UserRepository extends MongoRepository<User,Integer> {


}
