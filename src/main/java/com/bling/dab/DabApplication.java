package com.bling.dab;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.bling.dab.dao")
public class DabApplication {

    public static void main(String[] args) {
        SpringApplication.run(DabApplication.class, args);
    }

}

