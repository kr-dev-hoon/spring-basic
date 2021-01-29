package dev.hoon.basic.global.config.data;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@EnableConfigurationProperties(value = { DataSourceProperties.class })
public class JpaDataSource {

    private final DatabaseProperty databaseProperty;

    public JpaDataSource(DatabaseProperty databaseProperty) {

        this.databaseProperty = databaseProperty;
    }

    @Bean
    public DataSource subDataSource() {

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(databaseProperty.getDriverClassName());
        dataSourceBuilder.url(databaseProperty.getSub().getUrl());
        dataSourceBuilder.username(databaseProperty.getUsername());
        dataSourceBuilder.password(databaseProperty.getPassword());
        return dataSourceBuilder.build();
    }

    @Bean
    public DataSource mainDataSource() {

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(databaseProperty.getDriverClassName());
        dataSourceBuilder.url(databaseProperty.getUrl());
        dataSourceBuilder.username(databaseProperty.getUsername());
        dataSourceBuilder.password(databaseProperty.getPassword());
        return dataSourceBuilder.build();
    }

    @Bean
    public DataSource routingDataSource(@Qualifier("mainDataSource") DataSource mainDataSource,
            @Qualifier("subDataSource") DataSource subDataSource) {

        RoutingDataSource replicationRoutingDataSource = new RoutingDataSource();

        Map<Object, Object> dataSourceMap = new LinkedHashMap<>();
        dataSourceMap.put("main", mainDataSource);
        dataSourceMap.put("sub", subDataSource);

        replicationRoutingDataSource.setTargetDataSources(dataSourceMap);
        replicationRoutingDataSource.setDefaultTargetDataSource(mainDataSource);
        return replicationRoutingDataSource;
    }

    @Bean
    public DataSource dataSource(DataSource routingDataSource) {

        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
