package com.petproject;

import com.zaxxer.hikari.HikariDataSource;
import java.util.TimeZone;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestApplication {

    private static volatile boolean liquibaseProcessed = false;
    private static volatile DataSource dataSource;

    @Bean
    @ConditionalOnMissingBean(DataSource.class)
    public DataSource dataSource() {
        if (dataSource == null) {
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
            final HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(System.getProperty("spring.datasource.url"));
            dataSource.setUsername(System.getProperty("spring.datasource.username"));
            dataSource.setPassword(System.getProperty("spring.datasource.password"));
            TestApplication.dataSource = dataSource;
            return dataSource;
        } else {
            return dataSource;
        }
    }

    @Bean
    @ConditionalOnMissingBean(SpringLiquibase.class)
    public SpringLiquibase liquibase(final DataSource dataSource) {
        if (liquibaseProcessed){
            return null;
        } else {
            liquibaseProcessed = true;
            final SpringLiquibase liquibase = new SpringLiquibase();
            liquibase.setChangeLog("classpath:/db/changelog/xml/changelog-master.xml");
            liquibase.setDataSource(dataSource);
            liquibase.setLiquibaseSchema("liquibase");
            return liquibase;
        }
    }
}
