package com.bling.dab.dao;

import com.bling.dab.domain.SignIn;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: hxp
 * @date: 2019/4/4 10:03
 * @description:
 */
public interface SignInMapper extends JpaRepository<SignIn,Integer> {

    SignIn querySignIn(SignIn in);
}
