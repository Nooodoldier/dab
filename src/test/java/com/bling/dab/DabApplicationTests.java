package com.bling.dab;


import com.alibaba.fastjson.JSON;
import com.bling.dab.common.config.RedisConfig;
import com.bling.dab.common.model.SysRoleReq;
import com.bling.dab.common.model.UserInfoReq;
import com.bling.dab.common.result.Result;
import com.bling.dab.common.util.RedisUtil;
import com.bling.dab.dao.UserMapper;
import com.bling.dab.domain.SysRole;
import com.bling.dab.domain.User;
import com.bling.dab.domain.UserInfo;
import com.bling.dab.service.SysRoleService;
import com.bling.dab.service.UserInfoService;
import com.bling.dab.service.UserMongo;
import com.bling.dab.service.UserService;
import com.bling.dab.common.task.AsyncTask;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={DabApplication.class})
public class DabApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(DabApplicationTests.class);

    private final static StopWatch sw = new StopWatch();
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserService userService;
    @Resource
    private UserMongo userMongo;
    @Resource
    private RedisConfig redisConfig;
    @Resource
    private AsyncTask asyncTask;
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

    @Test
    public void test() throws Exception {

        long start = System.currentTimeMillis();

        asyncTask.doTaskOne();
        asyncTask.doTaskTwo();
        asyncTask.doTaskThree();
        Future<String> task4 = asyncTask.doTaskFour();
        while(true) {
            if(task4.isDone()) {
                System.out.println("task4执行"+task4.isDone());
                // 4个任务都调用完成，退出循环等待
                break;
            }
            Thread.sleep(1000);
        }

        long end = System.currentTimeMillis();

        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");

    }

    @Test
    public  void timerTaskTest()throws Exception{

        TimerTask timerTask = new TimerTask(){
            @Override
            public void run(){
                System.out.print("timer Task RUN"+System.currentTimeMillis()+"\n");
            }
        };


        Timer timer = new Timer();
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。这里是每3秒执行一次

        sw.start();
        timer.schedule(timerTask,3,2000);
        Thread.sleep(5000);
        sw.stop();
        System.out.println("执行时间"+sw.getLastTaskTimeMillis());
    }
    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void saveUserInfoTest(){
        UserInfoReq userInfoReq = new UserInfoReq();
        userInfoReq.setName("玛丽");
        userInfoReq.setPassword("123456");
        Result result = userInfoService.saveUser(userInfoReq);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result));
    }

    @Test
    public void saveUserTestAndRoleTest(){
        UserInfoReq userInfoReq = new UserInfoReq();
        userInfoReq.setName("玛丽8");
        userInfoReq.setPassword("123456");
        Set<Integer> set = new HashSet<>();
        set.add(6);
        set.add(7);
        userInfoReq.setSets(set);
        Result result = userInfoService.saveUser(userInfoReq);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result));
    }

    @Test
    public void deleteUserInfo(){
        UserInfoReq userInfoReq = new UserInfoReq();
        HashSet<Integer> sets = Sets.newHashSet();
        sets.add(4);
        sets.add(9);
        sets.add(10);
        userInfoReq.setSets(sets);
        Result result = userInfoService.deleteUser(userInfoReq);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result));

    }

    @Test
    public void updateUserTest(){
        UserInfoReq userInfoReq = new UserInfoReq();
        userInfoReq.setUid(17);
        userInfoReq.setName("马云");
        Result result = userInfoService.updateUser(userInfoReq);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result));
    }

    @Test
    public void findByUsername(){
        UserInfo info = userInfoService.findByUsername("48VymxD89y53v65MSn42IvSr0HY=");
        Assert.assertNotNull(info);
        logger.info(JSON.toJSONString(info));
    }
    @Test
    public void findByUid(){
        Result result = userInfoService.findByUid(4);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result.getData()));
    }
    @Test
    public void getByUid(){
        Result result = userInfoService.getByUid(4);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result.getData()));
    }
    @Test
    public void getOne(){
        //Result result = userInfoService.getOne(4);
        Result result = userInfoService.findById(4);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result.getData()));
        //存在懒加载的问题所以不用

    }

    @Test
    public void findAllById(){
        List<Integer> list = Arrays.asList(4);
        Result result = userInfoService.findAllById(list);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result.getData()));
    }

    @Test
    public void findPageAll(){
        UserInfoReq userInfoReq = new UserInfoReq();
        userInfoReq.setCurrentPage("0");
        userInfoReq.setPageSize("10");
        userInfoReq.setOrder("uid");
        Result result = userInfoService.findAll(userInfoReq);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result));

    }

    @Autowired
    private SysRoleService sysRoleService;
    @Test
    public void saveRole(){
        SysRoleReq sysRoleReq = new SysRoleReq();
        sysRoleReq.setId(null);
        sysRoleReq.setAvailable(Boolean.TRUE);
        sysRoleReq.setDescription("admin");
        sysRoleReq.setRole("admin");
        Result result = sysRoleService.saveRole(sysRoleReq);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result));
    }
    @Test
    public void saveAllRole(){
        SysRoleReq sysRoleReq = new SysRoleReq();
        sysRoleReq.setId(null);
        sysRoleReq.setAvailable(Boolean.TRUE);
        sysRoleReq.setDescription("client");
        sysRoleReq.setRole("client");

        SysRoleReq sysRoleReq1 = new SysRoleReq();
        sysRoleReq1.setId(null);
        sysRoleReq1.setAvailable(Boolean.TRUE);
        sysRoleReq1.setDescription("client1");
        sysRoleReq1.setRole("client1");
        ArrayList<SysRoleReq> list = Lists.newArrayList();
        list.add(sysRoleReq);
        list.add(sysRoleReq1);
        Result result = sysRoleService.saveBatchRole(list);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result));
    }

}

