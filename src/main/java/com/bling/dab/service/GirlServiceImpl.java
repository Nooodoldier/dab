package com.bling.dab.service;

import com.bling.dab.domain.Girl;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: hxp
 * @date: 2019/5/17 15:13
 * @description:
 */
@WebService(serviceName = "GirlService",
            targetNamespace = "http://service.dab.bling.com",
            endpointInterface = "com.bling.dab.service.GirlService")
@Component
public class GirlServiceImpl implements GirlService{

    private Map<String, Girl> girlMap = new HashMap<String, Girl>();

    public GirlServiceImpl(){
        System.out.println("向实体类插入数据");
        Girl girl = new Girl();
        girl.setGid(UUID.randomUUID().toString().replace("-", ""));
        girl.setGname("test1");
        girl.setGemail("maplefix@163.xom");
        girlMap.put(girl.getGid(), girl);

        girl = new Girl();
        girl.setGid(UUID.randomUUID().toString().replace("-", ""));
        girl.setGname("test2");
        girl.setGemail("maplefix@163.xom");
        girlMap.put(girl.getGid(), girl);

        girl = new Girl();
        girl.setGid(UUID.randomUUID().toString().replace("-", ""));
        girl.setGname("test3");
        girl.setGemail("maplefix@163.xom");
        girlMap.put(girl.getGid(), girl);
    }
    @Override
    public Girl getGirl(String gid) {
        System.out.println("userMap是:"+girlMap);
        return girlMap.get(gid);
    }

    @Override
    public String getGname(String gid) {
        System.out.println("userMap是:"+girlMap);
        return girlMap.get(gid).getGname();
    }
}
