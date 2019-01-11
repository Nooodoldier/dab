package com.bling.dab;


import com.bling.dab.dao.UserMapper;
import com.bling.dab.domain.User;
import com.bling.dab.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={DabApplication.class})
public class DabApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(DabApplicationTests.class);

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserService userService;
    @Test
    public void contextLoads() {

    }

    @Test
    public void booMapper() {
        User user = new User();
        user.setId(6);
        user.setName("好嗨哟");
        int i = userMapper.saveUser(user);
        logger.info("=======save========"+(i>0));
    }
    @Test
    public void booService() {
        User user = new User();
        user.setId(9);
        user.setName("好嗨哟");
        int i = userService.saveUser(user);
        logger.info("=======save========"+(i>0));
    }
}

