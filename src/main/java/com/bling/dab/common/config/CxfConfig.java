package com.bling.dab.common.config;

import com.bling.dab.service.GirlService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @author: hxp
 * @date: 2019/5/17 15:23
 * @description:cxf发布webservice配置
 */
@Configuration
public class CxfConfig {

    @Autowired
    private Bus bus;
    @Autowired
    GirlService girlService;



    /** JAX-WS
     * 站点服务
     * **/
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, girlService);
        endpoint.publish("/GirlService");
        return endpoint;
    }

}
