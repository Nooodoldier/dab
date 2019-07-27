package com.bling.dab;

import org.mybatis.spring.annotation.MapperScan;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.bling.dab.dao")
public class DabApplication {


    //打jar
    public static void main(String[] args) {
        SpringApplication.run(DabApplication.class, args);
        System.out.println("dab启动完成--------");
    }


}

