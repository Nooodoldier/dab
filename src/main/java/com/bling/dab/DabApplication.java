package com.bling.dab;

import org.mybatis.spring.annotation.MapperScan;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@EnableSwagger2
@SpringBootApplication
@EnableJpaRepositories(basePackages = { "com.bling.dab.dao" }, entityManagerFactoryRef = "entityManagerFactoryPrimary", transactionManagerRef = "transactionManagerPrimary")
@EnableTransactionManagement
@MapperScan("com.bling.dab.mapper")
public class DabApplication {

    public static void main(String[] args) {
        SpringApplication.run(DabApplication.class, args);
        System.out.println("dab启动完成--------OK！");
    }

}
