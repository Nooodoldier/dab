package com.bling.dab.controller;

import com.bling.dab.common.annotation.Log;
import com.bling.dab.common.result.Result;
import com.bling.dab.domain.LoginUser;
import com.bling.dab.domain.SignIn;
import com.bling.dab.domain.User;
import com.bling.dab.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * @author: hxp
 * @date: 2019/2/15 19:40
 * @description:
 */
@ApiIgnore
@RestController
@Api(value = "类描述", tags = {"显示的标签"})
@RequestMapping("/user")
public class UserController {

    /**创建线程安全的Map*/
    static Map<Integer, User> users = Collections.synchronizedMap(new HashMap<Integer, User>());

    @Autowired
    private LoginService loginService;

    @Log(modelName = "用户管理模块",description = "用户相关信息维护",action = "用户信息查找")
    @ApiOperation(value="用户信息查找", notes="根据url的name和password来获取用户详细信息")
    @ApiImplicitParam(name = "name", value = "用户name", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "user/{name}/{password}", method = RequestMethod.GET)
    public ResponseEntity<Result> getUserByNameAndPassword (@PathVariable(value = "username") String username , @PathVariable(value = "password") String password){
        Result r = new Result();
        try {
            SignIn in = new SignIn();
            in.setUserName(username);
            in.setPassword(password);
            LoginUser loginUser = loginService.selectLoginUserByUsername(username);
            r.setData(loginUser);
            r.setSuccess(true);
            r.setMessage("ok");
        } catch (Exception e) {
            r.setMessage(e.getClass().getName() + ":" + e.getMessage());
            r.setSuccess(false);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public ResponseEntity<Result> getUserById (@PathVariable(value = "id") Integer id){
        Result r = new Result();
        try {
            User user = users.get(id);
            r.setSuccess(true);
            r.setMessage("ok");
        } catch (Exception e) {
            r.setMessage(e.getClass().getName() + ":" + e.getMessage());
            r.setSuccess(false);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 查询用户列表
     * @return
     */
    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public ResponseEntity<Result> getUserList (){
        Result r = new Result();
        try {
            List<User> userList = new ArrayList<User>(users.values());
            r.setSuccess(true);
            r.setMessage("ok");
        } catch (Exception e) {
            r.setMessage(e.getClass().getName() + ":" + e.getMessage());
            r.setSuccess(false);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "user", method = RequestMethod.POST)
    public ResponseEntity<Result> add (@RequestBody User user){
        Result r = new Result();
        try {
            users.put(user.getId(), user);
            r.setSuccess(true);
            r.setMessage("ok");
        } catch (Exception e) {
            r.setMessage(e.getClass().getName() + ":" + e.getMessage());
            r.setSuccess(false);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @ApiOperation(value="删除用户", notes="根据url的id来指定删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Result> delete (@PathVariable(value = "id") Integer id){
        Result r = new Result();
        try {
            users.remove(id);
            r.setSuccess(true);
            r.setMessage("ok");
        } catch (Exception e) {
            r.setMessage(e.getClass().getName() + ":" + e.getMessage());
            r.setSuccess(false);

            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 根据id修改用户信息
     * @param user
     * @return
     */
    @ApiOperation(value="更新信息", notes="根据url的id来指定更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long",paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "User")
    })
    @RequestMapping(value = "user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Result> update (@PathVariable("id") Integer id, @RequestBody User user){
        Result r = new Result();
        try {
            User u = users.get(id);
            u.setName(user.getName());
            u.setAge(user.getAge());
            users.put(id, u);
            r.setData(u);
            r.setMessage("ok");
            r.setSuccess(true);
        } catch (Exception e) {
            r.setMessage(e.getClass().getName() + ":" + e.getMessage());
            r.setSuccess(false);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    /**使用该注解忽略这个API*/
    @ApiIgnore
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String  jsonTest() {
        return " hi you!";
    }
}
