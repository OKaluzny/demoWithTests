//package com.example.demowithtests.util.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//import javax.sql.DataSource;
//@Configuration
//public class JpaConfig {
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setDataSource(dataSource);
//        factoryBean.setPackagesToScan("com.example.demowithtests.domain");
//        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        // factoryBean.setJpaProperties(hibernateProperties());
//        return factoryBean;
//    }
//}
