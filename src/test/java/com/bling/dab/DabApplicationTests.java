package com.bling.dab;


import com.alibaba.fastjson.JSON;
import com.bling.dab.common.redis.RedisConfig;
import com.bling.dab.common.redis.RedisUtil;
import com.bling.dab.dao.UserMapper;
import com.bling.dab.domain.User;
import com.bling.dab.service.UserMongo;
import com.bling.dab.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={DabApplication.class})
public class DabApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(DabApplicationTests.class);

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserService userService;
    @Resource
    private RedisConfig redisConfig;
    @Resource
    private UserMongo userMongo;
    @Test
    public void booMapper() {
        User user = new User();
        user.setName("好嗨哟");
        int i = userMapper.saveUser(user);
        logger.info("=======save========"+(i>0));
    }
    @Test
    public void booService() {
        User user = new User();
        user.setName("好嗨哟");
        int i = userService.saveUser(user);
        logger.info("=======save========"+(i>0));
    }
    @Test
    public void redisService(){
        RedisUtil redisUtil = RedisUtil.getInstance(redisConfig);
        boolean mykey = redisUtil.exists("mykey");
        if(mykey){
            String mykey1 = redisUtil.get("mykey");
            logger.info("mykey已存在："+mykey1);
            Long expire = redisUtil.expire("mykey", 60 * 60 * 24);
            logger.info("设置mykey的超时时间："+expire);
            String type = redisUtil.type("mykey");
            logger.info("mykey的类型："+type);
        }

    }

    @Test
    public void saveUser1(){
        User user = new User();
        user.setId(7);
        user.setName("张浩");
        user.setAge(10);
        User saveUser = userMongo.saveUser1(user);
        logger.info("mongodb新增："+ JSON.toJSONString(saveUser));
    }
    @Test
    public void saveUser2(){
        User user = new User();
        user.setId(6);
        user.setName("赵六");
        User saveUser = userMongo.saveUser2(user);
        logger.info("mongodb新增："+ JSON.toJSONString(saveUser));
    }
    @Test
    public void insertUser(){
        User user = new User();
        user.setId(3);
        user.setName("张浩");
        User saveUser = userMongo.insertUser(user);
        logger.info("mongodb新增："+ JSON.toJSONString(saveUser));
    }

    @Test
    public void findUser(){
        List<User> all = userMongo.findAll();
        logger.info("mongodb查询所有："+ JSON.toJSONString(all));
    }

    @Test
    public void findUserSort(){
        User user = new User();
        user.setName("张浩");
        List<User> all = userMongo.findUserSort(user);
        logger.info("mongodb查询所有："+ JSON.toJSONString(all));
    }

    @Test
    public void findUserByName(){
        User user = userMongo.findUserByName("赵六");
        logger.info("mongodb查询所有："+ JSON.toJSONString(user));
    }

    @Test
    public void updateUser(){
        User user = new User();
        user.setId(0);
        user.setName("王五");
        boolean b = userMongo.updateUser(user);
        logger.info("mongodb查询所有："+ JSON.toJSONString(b));
    }

    @Test
    public void deleteUserById(){
        boolean b = userMongo.deleteUserById(2);
        logger.info("mongodb查询所有："+ JSON.toJSONString(b));
    }
}

