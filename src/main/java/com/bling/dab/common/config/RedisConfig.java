package com.bling.dab.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author: hxp
 * @date: 2019/1/30 11:50
 * @description:
 */
@Configuration
public class RedisConfig {

    @Value("${cache.host}")
    private String host;
    @Value("${cache.port}")
    private String port;
    @Value("${cache.pwd}")
    private String pwd;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
