package com.bling.dab.service;

import com.bling.dab.domain.Girl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author: hxp
 * @date: 2019/5/17 15:08
 * @description:
 */
@WebService
public interface GirlService {

    @WebMethod
    Girl getGirl(@WebParam(name = "gid") String gid);

    @WebMethod
    @WebResult(name = "String",targetNamespace = "")
    String getGname(@WebParam(name = "gid") String gid);
}
