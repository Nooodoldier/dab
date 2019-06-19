package com.bling.dab.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @author: hxp
 * @date: 2019/6/18 10:54
 * @description:
 */
@Configuration
@EnableConfigurationProperties(JpaProperties.class)
@AutoConfigureAfter(DataSourceConfig.class)
public class JpaEntityManager {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private DataSource myRoutingDataSource;

    @Bean(name = "entityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        Map<String, String> properties = jpaProperties.getProperties();
        //要设置这个属性，实现 CamelCase -> UnderScore 的转换
        properties.put("hibernate.physical_naming_strategy",
                "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");


        return builder
                .dataSource(myRoutingDataSource)
                .properties(properties)
                .packages("com.bling.dab.domain")
                .persistenceUnit("myPersistenceUnit")
                .build();
    }

    @Primary
    @Bean(name = "entityManagerFactoryPrimary")
    public EntityManagerFactory entityManagerFactory(EntityManagerFactoryBuilder builder) {
        return this.entityManagerFactoryBean(builder).getObject();
    }

    @Primary
    @Bean(name = "transactionManagerPrimary")
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactory(builder));
    }

}

