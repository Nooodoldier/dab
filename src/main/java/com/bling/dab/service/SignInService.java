package com.bling.dab.service;

import com.bling.dab.dao.SignInMapper;
import com.bling.dab.domain.SignIn;
import org.springframework.stereotype.Service;

/**
 * @author: hxp
 * @date: 2019/4/4 11:51
 * @description:
 */
@Service
public class SignInService {

    private SignInMapper signInMapper;

    public SignIn querySignIn(){
        return signInMapper.querySignIn();
    }
}
