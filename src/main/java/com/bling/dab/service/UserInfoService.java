package com.bling.dab.service;

import com.bling.dab.common.model.UserInfoReq;
import com.bling.dab.common.result.Result;
import com.bling.dab.common.util.EncryptUtil;
import com.bling.dab.dao.SysRoleRepository;
import com.bling.dab.dao.UserInfoRepository;
import com.bling.dab.domain.SysRole;
import com.bling.dab.domain.UserInfo;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author: hxp
 * @date: 2019/5/10 18:38
 * @description:用户管理
 */
@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    public UserInfo findByUsername(String username) {

        return userInfoRepository.findByUsername(username);
    }

    public UserInfo findUserInfoByUid(Integer uid) {

        return userInfoRepository.findByUid(uid);
    }


    public Result findByUid(Integer uid) {
        UserInfo info = userInfoRepository.findByUid(uid);
        return Result.success(info);
    }

    public Result getByUid(Integer uid) {
        UserInfo info = userInfoRepository.getByUid(uid);
        return Result.success(info);
    }
    public Result getOne(Integer uid) {
        UserInfo info = userInfoRepository.getOne(uid);
        return Result.success(info);
    }
    public Result findById(Integer uid) {
        //UserInfo info = userInfoRepository.findById(uid).get();
        UserInfo userInfo = new UserInfo();
        userInfo.setName("丽丽");
        //创建匹配器，即如何使用查询条件
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
//                .withMatcher("name",ExampleMatcher.GenericPropertyMatchers.endsWith())
//                .withMatcher("name",ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("salt");
        Example<UserInfo> example = Example.of(userInfo,exampleMatcher);
        UserInfo info = userInfoRepository.findOne(example).get();
        return Result.success(info);
    }

    public Result findAllById(List<Integer> list){
        List<UserInfo> all = userInfoRepository.findAllById(list);
        return Result.success(all);
    }
    /**
     * 分页查询
     * @param userInfoReq
     * @return
     */
    public Page<UserInfo> findAll(UserInfoReq userInfoReq) {
        int page = NumberUtils.toInt(userInfoReq.getCurrentPage(), 0);
        int size = NumberUtils.toInt(userInfoReq.getPageSize(), 10);
        String order = userInfoReq.getOrder();
        UserInfo userInfo = new UserInfo();
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, order);
        Example<UserInfo> example = Example.of(userInfo);
        Page<UserInfo> infoPage = userInfoRepository.findAll(example, pageable);
        return infoPage;
    }

    /**
     * 新增用户
     * @param userInfoReq
     * @return
     */
    @Transactional(rollbackFor = Exception.class ,propagation = Propagation.REQUIRED ,isolation = Isolation.READ_COMMITTED)
    public Result saveUser(UserInfoReq userInfoReq){
        UserInfo userInfo = new UserInfo();
        userInfo.setName(userInfoReq.getName());
        EncryptUtil encryptUtil = EncryptUtil.getInstance();
        userInfo.setUsername(encryptUtil.SHA1(UUID.randomUUID().toString().replace("-","")).substring(0,28));
        String salt = encryptUtil.createSalt();
        userInfo.setPassword(encryptUtil.MD5(encryptUtil.Base64Encode(userInfoReq.getPassword())+salt));
        userInfo.setSalt(salt);
        userInfo.setState(Byte.valueOf("0"));
        Set<Integer> sets = userInfoReq.getSets();
        UserInfo info = userInfoRepository.save(userInfo);
        if(!CollectionUtils.isEmpty(sets)){
            Iterator<Integer> iterator = sets.iterator();
            while (iterator.hasNext()){
                Integer next = iterator.next();
                userInfoRepository.saveUserRole(info.getUid(),next);
            }
        }
        return Result.success(info);
    }

    @Transactional(rollbackFor = Exception.class ,propagation = Propagation.REQUIRED ,isolation = Isolation.READ_COMMITTED)
    public Result updateUserRole(UserInfoReq userInfoReq){
        Integer uid = userInfoReq.getUid();
        Set<Integer> sets = userInfoReq.getSets();
        userInfoRepository.deleteUserRole(uid);
        Iterator<Integer> iterator = sets.iterator();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            userInfoRepository.saveUserRole(uid,next);
        }
        return Result.success();
    }

    /**
     * 批量删除\n
     * 删除用户，级联删除用户角色关系
     * @param userInfoReq
     * @return
     */
    public Result deleteUser(UserInfoReq userInfoReq){
        Set<Integer> uids = userInfoReq.getSets();
        HashSet<UserInfo> infos = new HashSet<>();
        uids.forEach((t)->{
            UserInfo info = userInfoRepository.getOne(t);
            infos.add(info);
        });
        userInfoRepository.deleteInBatch(infos);
        return Result.success();
    }

    /**
     * 修改user
     * @param userInfoReq
     * @return
     */
    @Transactional(rollbackFor = Exception.class ,propagation = Propagation.REQUIRED ,isolation = Isolation.READ_COMMITTED)
    public Result updateUser(UserInfoReq userInfoReq){
        Integer uid = userInfoReq.getUid();
        String name = userInfoReq.getName();
        int i = userInfoRepository.updateUser(name, uid);
        return Result.success(i);
    }
}
