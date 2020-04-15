package com.angel.presto.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.angel.presto.mapper", sqlSessionTemplateRef  = "marketingSqlSessionTemplate")
public class DataSourcePrestoConfig {
    private final static Logger logger = LoggerFactory.getLogger(DataSourcePrestoConfig.class);
    @Bean(name = "marketingDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.presto")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "marketingSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("marketingDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        /*加载mybatis全局配置文件*/
        //bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/mybatis-config.xml"));
        /*加载所有的mapper.xml映射文件*/
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        logger.info("loading presto connection......");
        return bean.getObject();
    }

    @Bean(name = "marketingTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("marketingDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "marketingSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("marketingSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
