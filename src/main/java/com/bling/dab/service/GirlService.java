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
@WebService(name = "GirlService",//暴露服务名称
                targetNamespace = "http://service.dab.bling.com/")// 命名空间,一般是接口的包名倒序
public interface GirlService {

    @WebMethod
    String getGirl(@WebParam(name = "gname") String gname);

    @WebMethod
    @WebResult(name = "String",targetNamespace = "")
    String getGname(@WebParam(name = "gname") String gname);
}
