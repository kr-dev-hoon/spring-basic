package dev.hoon.basic.global.config.data;

import org.hibernate.cfg.AvailableSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Component
@EnableTransactionManagement
@EnableConfigurationProperties(value = { JpaProperties.class })
public class JpaConfig {

    private Logger logger = LoggerFactory.getLogger(JpaConfig.class);

    private JpaProperties jpaProperties;

    @Autowired
    private void setJpaProperties(JpaProperties jpaProperties) {

        this.jpaProperties = jpaProperties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource) {

        Properties jpaProperties = new Properties();

        jpaProperties.putAll(this.jpaProperties.getProperties());

        jpaProperties.put(AvailableSettings.DATASOURCE, dataSource);

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(4);
        threadPoolTaskExecutor.setDaemon(true);
        threadPoolTaskExecutor.afterPropertiesSet();

        emf.setBootstrapExecutor(threadPoolTaskExecutor);

        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setPackagesToScan("dev.hoon.basic.domain.*.model");
        emf.setJpaProperties(jpaProperties);

        return emf;
    }

    @Primary
    @Bean
    public EntityManagerFactory entityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {

        return entityManagerFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager tm = new JpaTransactionManager();

        tm.setEntityManagerFactory(entityManagerFactory);

        return tm;
    }

}

