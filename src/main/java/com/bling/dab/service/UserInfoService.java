package com.bling.dab.service;

import com.bling.dab.domain.UserInfo;
import org.springframework.stereotype.Service;

/**
 * @author: hxp
 * @date: 2019/5/10 18:38
 * @description:
 */
@Service
public class UserInfoService {
    public UserInfo findByUsername(String username) {
        UserInfo user = new UserInfo();
        user.setName(username);
        return user;
    }
}
