package com.bling.dab.service;

import com.bling.dab.common.model.UserInfoReq;
import com.bling.dab.common.result.Result;
import com.bling.dab.common.util.EncryptUtil;
import com.bling.dab.dao.UserInfoRepository;
import com.bling.dab.domain.UserInfo;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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


    public Result findByUid(Integer uid) {
        UserInfo info = userInfoRepository.getOne(uid);
        return Result.success(info);
    }


    /**
     * 分页查询
     * @param userInfoReq
     * @return
     */
    public Result findAll(UserInfoReq userInfoReq) {
        int page = NumberUtils.toInt(userInfoReq.getCurrentPage(), 1);
        int size = NumberUtils.toInt(userInfoReq.getPageSize(), 10);
        String order = userInfoReq.getOrder();
        UserInfo userInfo = new UserInfo();
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, order);
        Example<UserInfo> example = Example.of(userInfo);
        Page<UserInfo> infoPage = userInfoRepository.findAll(example, pageable);
        return Result.success(infoPage);
    }

    /**
     * 新增用户
     * @param userInfoReq
     * @return
     */
    public Result saveUser(UserInfoReq userInfoReq){
        UserInfo userInfo = new UserInfo();
        userInfo.setName(userInfoReq.getName());
        EncryptUtil encryptUtil = EncryptUtil.getInstance();
        userInfo.setUsername(encryptUtil.SHA1(UUID.randomUUID().toString().replace("-","")).substring(0,28));
        String salt = encryptUtil.createSalt();
        userInfo.setPassword(encryptUtil.MD5(encryptUtil.Base64Encode(userInfoReq.getPassword())+salt));
        userInfo.setSalt(salt);
        userInfo.setState(Byte.valueOf("0"));
        UserInfo save = userInfoRepository.save(userInfo);
        return Result.success(save);
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

}
