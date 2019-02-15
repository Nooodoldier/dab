package com.bling.dab.service;

import com.bling.dab.domain.User;
import com.bling.dab.mongo.UserRepository;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: hxp
 * @date: 2019/2/15 16:24
 * @description:
 */
@Service
public class UserMongo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    /**
     * MongoRepository的缺点是不够灵活，MongoTemplate正好可以弥补不足
     * @param user
     * @return
     */
    /**
     * MongoTemplate是数据库和代码之间的接口，对数据库的操作都在它里面
     * 1.MongoTemplate实现了interface MongoOperations。
     * 2.MongoDB documents和domain classes之间的映射关系是通过实现了MongoConverter这个interface的类来实现的。
     * 3.MongoTemplate提供了非常多的操作MongoDB的方法。 它是线程安全的，可以在多线程的情况下使用。
     * 4.MongoTemplate实现了MongoOperations接口, 此接口定义了众多的操作方法如"find", "findAndModify", "findOne", "insert", "remove", "save", "update" and "updateMulti"等。
     * 5.MongoTemplate转换domain object为DBObject,缺省转换类为MongoMappingConverter,并提供了Query, Criteria, and Update等流式API。
     * MongoTemplate核心操作类：Criteria和Query
     * Criteria类：封装所有的语句，以方法的形式查询。
     * Query类：将语句进行封装或者添加排序之类的操作。
     * @param user
     * @return
     */
    public User saveUser1(User user){
        return userRepository.save(user);
    }

    public User insertUser(User user){
        return userRepository.insert(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public List<User> findUserSort(User user){

        // Sort sort = new Sort(Sort.Direction.ASC, "id").and(new Sort(Sort.Direction.ASC, "name"));//多条件DEVID、time
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        //查询条件
        Criteria criteria = Criteria.where("name").is(user.getName());
        Query query = new Query(criteria);
        return mongoTemplate.find(query.with(sort).limit(1000), User.class);
    }

    /**
     * 创建对象
     */
    public User saveUser2(User user) {
        return mongoTemplate.save(user);
    }

    /**
     * 根据用户名查询对象
     * @return
     */
    public User findUserByName(String name) {
        Query query=new Query(Criteria.where("name").is(name));
        User mgt =  mongoTemplate.findOne(query , User.class);
        return mgt;
    }

    /**
     * 更新对象
     */
    public boolean updateUser(User user) {
        Query query=new Query(Criteria.where("id").is(user.getId()));
        Update update= new Update().set("id", user.getId()).set("name", user.getName());
        //更新查询返回结果集的第一条
        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,TestEntity.class);
        return result.wasAcknowledged();

    }

    /**
     * 删除对象
     * @param id
     */
    public boolean deleteUserById(Integer id) {
        Query query=new Query(Criteria.where("id").is(id));
        DeleteResult result = mongoTemplate.remove(query, User.class);
        return result.wasAcknowledged();
    }

}
