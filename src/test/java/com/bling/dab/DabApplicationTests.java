package com.bling.dab;


import com.alibaba.fastjson.JSON;
import com.bling.dab.common.config.RedisConfig;
import com.bling.dab.common.model.SysRoleReq;
import com.bling.dab.common.model.UserInfoReq;
import com.bling.dab.common.result.Result;
import com.bling.dab.common.task.AsyncTask;
import com.bling.dab.common.util.RedisUtil;
import com.bling.dab.domain.*;
import com.bling.dab.service.*;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={DabApplication.class})
public class DabApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(DabApplicationTests.class);

    private final static StopWatch sw = new StopWatch();

    @Resource
    private UserMongo userMongo;
    @Resource
    private RedisConfig redisConfig;
    @Resource
    private AsyncTask asyncTask;

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
        UserInfo result = userInfoService.saveUser(userInfoReq);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result));
    }

    @Test
    public void saveUserAndRoleTest(){
        UserInfoReq userInfoReq = new UserInfoReq();
        userInfoReq.setName("马云");
        userInfoReq.setPassword("123456");
        Set<Integer> set = new HashSet<>();
        set.add(31);
        set.add(32);
        set.add(33);
        userInfoReq.setSets(set);
        UserInfo result = userInfoService.saveUser(userInfoReq);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result));
    }
    @Test
    public void saveUserAndRoleTest1(){
        UserInfoReq userInfoReq = new UserInfoReq();
        userInfoReq.setName("马化腾");
        userInfoReq.setPassword("123456");
        Set<Integer> set = new HashSet<>();
        set.add(32);
        set.add(33);
        userInfoReq.setSets(set);
        UserInfo result = userInfoService.saveUser(userInfoReq);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result));
    }
    @Test
    public void saveUserAndRoleTest2(){
        UserInfoReq userInfoReq = new UserInfoReq();
        userInfoReq.setName("王健林");
        userInfoReq.setPassword("123456");
        Set<Integer> set = new HashSet<>();
        set.add(33);
        userInfoReq.setSets(set);
        UserInfo result = userInfoService.saveUser(userInfoReq);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result));
    }


    @Test
    public void deleteUserInfo(){
        UserInfoReq userInfoReq = new UserInfoReq();
        String[] uid = new String[]{"11","12","13","14","15","16","17"};
        Set<Integer> sets = Arrays.asList(uid).stream().map((t) -> Integer.parseInt(t)).collect(Collectors.toSet());
        userInfoReq.setSets(sets);
        userInfoService.deleteUser(userInfoReq);
    }

    @Test
    public void updateUserTest(){
        UserInfoReq userInfoReq = new UserInfoReq();
        userInfoReq.setUid(17);
        userInfoReq.setName("马云");
        int i = userInfoService.updateUser(userInfoReq);
        Assert.assertNotNull(i);
        logger.info(JSON.toJSONString(i>0));
    }

    @Test
    public void findByUsername(){
        UserInfo info = userInfoService.findByUsername("GwKtCC1tZahE7IgkR9CZGrkEqm4=");
        Assert.assertNotNull(info);
       // logger.info(JSON.toJSONString(info));
    }
    @Test
    public void findByUid(){
        UserInfo result = userInfoService.findByUid(34);
        Assert.assertNotNull(result);
        //logger.info(JSON.toJSONString(result.getData()));
    }
    @Test
    public void getByUid(){
        UserInfo result = userInfoService.getByUid(4);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result));
    }
    @Test
    public void getOne(){
        //Result result = userInfoService.getOne(4);
        UserInfo result = userInfoService.findById(4);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result));
        //存在懒加载的问题所以不用

    }

    @Test
    public void findAllById(){
        List<Integer> list = Arrays.asList(34);
        List<UserInfo> all = userInfoService.findAllById(list);
        Assert.assertNotNull(all);
       // logger.info(JSON.toJSONString(result.getData()));
    }

    /**
     * JSON.toStirng()方法会执行懒加载报错，此时关联对象为代理对象，无法序列化
     */
    @Test
    public void findPageAll(){
        UserInfoReq userInfoReq = new UserInfoReq();
        userInfoReq.setCurrentPage("0");
        userInfoReq.setPageSize("10");
        userInfoReq.setOrder("uid");
        Page<UserInfo> all = userInfoService.findAll(userInfoReq);
        Assert.assertNotNull(all);
       // logger.info(JSON.toJSONString(all));

    }
    @Test
    public void getUserList(){
        List<UserInfo> userList = userInfoService.getUserList();
        Assert.assertNotNull(userList);
        //logger.info(JSON.toJSONString(userList));

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
        SysRole role = sysRoleService.saveRole(sysRoleReq);
        Assert.assertNotNull(role);
        logger.info(JSON.toJSONString(role));
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
        List<SysRole> sysRoles = sysRoleService.saveBatchRole(list);
        Assert.assertNotNull(sysRoles);
        logger.info(JSON.toJSONString(sysRoles));
    }
    @Test
    public void saveRolePermissionTest(){
        SysRoleReq sysRoleReq = new SysRoleReq();
        sysRoleReq.setRole("admin");
        sysRoleReq.setAvailable(Boolean.TRUE);
        sysRoleReq.setDescription("管理员");
        HashSet<Integer> sets = new HashSet<>();
        sets.add(18);
        sets.add(19);
        sets.add(20);
        sets.add(21);
        sets.add(22);
        sets.add(23);
        sets.add(24);
        sets.add(25);
        sets.add(26);
        sysRoleReq.setSets(sets);
        SysRole role = sysRoleService.saveRole(sysRoleReq);
        Assert.assertNotNull(role);
        logger.info(JSON.toJSONString(role));
    }
    @Test
    public void saveRolePermissionTest1(){
        SysRoleReq sysRoleReq = new SysRoleReq();
        sysRoleReq.setRole("tiger");
        sysRoleReq.setAvailable(Boolean.TRUE);
        sysRoleReq.setDescription("老虎");
        HashSet<Integer> sets = new HashSet<>();
        sets.add(21);
        sets.add(22);
        sets.add(23);
        sets.add(24);
        sets.add(25);
        sets.add(26);
        sysRoleReq.setSets(sets);
        SysRole role = sysRoleService.saveRole(sysRoleReq);
        Assert.assertNotNull(role);
        logger.info(JSON.toJSONString(role));
    }
    @Test
    public void saveRolePermissionTest2(){
        SysRoleReq sysRoleReq = new SysRoleReq();
        sysRoleReq.setRole("monkey");
        sysRoleReq.setAvailable(Boolean.TRUE);
        sysRoleReq.setDescription("猴子");
        HashSet<Integer> sets = new HashSet<>();
        sets.add(24);
        sets.add(25);
        sets.add(26);
        sysRoleReq.setSets(sets);
        SysRole role = sysRoleService.saveRole(sysRoleReq);
        Assert.assertNotNull(role);
        logger.info(JSON.toJSONString(role));
    }
    @Autowired
    private SysPermissionService sysPermissionService;
    @Test
    public void saveSysPermissionTest1(){
        SysPermission sysPermission = new SysPermission();
        sysPermission.setAvailable(Boolean.TRUE);
        sysPermission.setName("用户管理");
        sysPermission.setParentId(0L);
        sysPermission.setParentIds("0/");
        sysPermission.setPermission("userInfo:view");
        sysPermission.setResourceType("menu");
        sysPermission.setUrl("/userInfo/userList");
        SysPermission permission = sysPermissionService.saveSysPermission(sysPermission);
        Assert.assertNotNull(permission);
        logger.info(JSON.toJSONString(permission));
    }
    @Test
    public void saveSysPermissionTest11(){
        SysPermission sysPermission = new SysPermission();
        sysPermission.setAvailable(Boolean.TRUE);
        sysPermission.setName("用户添加");
        sysPermission.setParentId(0L);
        sysPermission.setParentIds("0/18");
        sysPermission.setPermission("userInfo:add");
        sysPermission.setResourceType("button");
        sysPermission.setUrl("/userInfo/userAdd");
        SysPermission permission = sysPermissionService.saveSysPermission(sysPermission);
        Assert.assertNotNull(permission);
        logger.info(JSON.toJSONString(permission));
    }
    @Test
    public void saveSysPermissionTest12(){
        SysPermission sysPermission = new SysPermission();
        sysPermission.setAvailable(Boolean.TRUE);
        sysPermission.setName("用户删除");
        sysPermission.setParentId(0L);
        sysPermission.setParentIds("0/18");
        sysPermission.setPermission("userInfo:del");
        sysPermission.setResourceType("button");
        sysPermission.setUrl("/userInfo/userDel");
        SysPermission permission = sysPermissionService.saveSysPermission(sysPermission);
        Assert.assertNotNull(permission);
        logger.info(JSON.toJSONString(permission));
    }
    @Test
    public void saveSysPermissionTest2(){
        SysPermission sysPermission = new SysPermission();
        sysPermission.setAvailable(Boolean.TRUE);
        sysPermission.setName("角色管理");
        sysPermission.setParentId(0L);
        sysPermission.setParentIds("0/");
        sysPermission.setPermission("sysRole:view");
        sysPermission.setResourceType("menu");
        sysPermission.setUrl("/sysRole/roleList");
        SysPermission permission = sysPermissionService.saveSysPermission(sysPermission);
        Assert.assertNotNull(permission);
        logger.info(JSON.toJSONString(permission));
    }
    @Test
    public void saveSysPermissionTest21(){
        SysPermission sysPermission = new SysPermission();
        sysPermission.setAvailable(Boolean.TRUE);
        sysPermission.setName("角色添加");
        sysPermission.setParentId(0L);
        sysPermission.setParentIds("0/19");
        sysPermission.setPermission("sysRole:add");
        sysPermission.setResourceType("button");
        sysPermission.setUrl("/sysRole/roleAdd");
        SysPermission permission = sysPermissionService.saveSysPermission(sysPermission);
        Assert.assertNotNull(permission);
        logger.info(JSON.toJSONString(permission));
    }
    @Test
    public void saveSysPermissionTest22(){
        SysPermission sysPermission = new SysPermission();
        sysPermission.setAvailable(Boolean.TRUE);
        sysPermission.setName("角色删除");
        sysPermission.setParentId(0L);
        sysPermission.setParentIds("0/19");
        sysPermission.setPermission("sysRole:del");
        sysPermission.setResourceType("button");
        sysPermission.setUrl("/sysRole/roleDel");
        SysPermission permission = sysPermissionService.saveSysPermission(sysPermission);
        Assert.assertNotNull(permission);
        logger.info(JSON.toJSONString(permission));
    }

    @Test
    public void saveSysPermissionTest3(){
        SysPermission sysPermission = new SysPermission();
        sysPermission.setAvailable(Boolean.TRUE);
        sysPermission.setName("权限管理");
        sysPermission.setParentId(0L);
        sysPermission.setParentIds("0/");
        sysPermission.setPermission("sysPermission:view");
        sysPermission.setResourceType("menu");
        sysPermission.setUrl("/sysPermission/permissionList");
        SysPermission permission = sysPermissionService.saveSysPermission(sysPermission);
        Assert.assertNotNull(permission);
        logger.info(JSON.toJSONString(permission));
    }
    @Test
    public void saveSysPermissionTest31(){
        SysPermission sysPermission = new SysPermission();
        sysPermission.setAvailable(Boolean.TRUE);
        sysPermission.setName("权限添加");
        sysPermission.setParentId(0L);
        sysPermission.setParentIds("0/20");
        sysPermission.setPermission("sysPermission:add");
        sysPermission.setResourceType("button");
        sysPermission.setUrl("/sysPermission/permissionAdd");
        SysPermission permission = sysPermissionService.saveSysPermission(sysPermission);
        Assert.assertNotNull(permission);
        logger.info(JSON.toJSONString(permission));
    }
    @Test
    public void saveSysPermissionTest32(){
        SysPermission sysPermission = new SysPermission();
        sysPermission.setAvailable(Boolean.TRUE);
        sysPermission.setName("权限删除");
        sysPermission.setParentId(0L);
        sysPermission.setParentIds("0/20");
        sysPermission.setPermission("sysPermission:del");
        sysPermission.setResourceType("button");
        sysPermission.setUrl("/sysPermission/permissionDel");
        SysPermission permission = sysPermissionService.saveSysPermission(sysPermission);
        Assert.assertNotNull(permission);
        logger.info(JSON.toJSONString(permission));
    }

    @Test
    public void saveSysPermissionTest13(){
        SysPermission sysPermission = new SysPermission();
        sysPermission.setAvailable(Boolean.TRUE);
        sysPermission.setName("用户关联角色修改");
        sysPermission.setParentId(0L);
        sysPermission.setParentIds("0/18");
        sysPermission.setPermission("userInfo:updateUserRole");
        sysPermission.setResourceType("button");
        sysPermission.setUrl("/userInfo/updateUserRole");
        SysPermission permission = sysPermissionService.saveSysPermission(sysPermission);
        Assert.assertNotNull(permission);
        logger.info(JSON.toJSONString(permission));
    }
    @Test
    public void saveSysPermissionTest23(){
        SysPermission sysPermission = new SysPermission();
        sysPermission.setAvailable(Boolean.TRUE);
        sysPermission.setName("角色关联权限修改");
        sysPermission.setParentId(0L);
        sysPermission.setParentIds("0/19");
        sysPermission.setPermission("sysRole:updateRolePermission");
        sysPermission.setResourceType("button");
        sysPermission.setUrl("/sysRole/updateRolePermission");
        SysPermission permission = sysPermissionService.saveSysPermission(sysPermission);
        Assert.assertNotNull(permission);
        logger.info(JSON.toJSONString(permission));
    }



    @Autowired
    private LoginService loginService;

    @Test
    public void saveLoginUser(){
        LoginUser record = new LoginUser();
        record.setUsername("张大大");
        record.setPassword("123456");
        Result result = loginService.saveLoginUser(record);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result));
    }
    @Test
    public void selectByPrimaryKey(){
        LoginUser loginUser = loginService.selectByPrimaryKey(1);
        loginService.selectByPrimaryKey(1);
        Assert.assertNotNull(loginUser);
        logger.info(JSON.toJSONString(loginUser));
    }
    @Test
    public void selectByExample(){
        Result result = loginService.selectByExample(1, 5);
        Assert.assertNotNull(result);
        logger.info(JSON.toJSONString(result));
    }


}

