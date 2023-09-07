package com.petproject;

import org.testcontainers.containers.MySQLContainer;

import static java.util.Collections.singletonMap;

public class MySQLContainerRule extends MySQLContainer<MySQLContainerRule> {
    private static final String IMAGE_VERSION = "mysql:8.0.26";

    private static volatile MySQLContainerRule instance;

    public static MySQLContainerRule getInstance(){
        if (instance == null){
            instance = new MySQLContainerRule();
        }
        return instance;
    }

    public MySQLContainerRule() {
        super(IMAGE_VERSION);
        withReuse(true);
        withTmpFs(singletonMap("/var/lib/mysql", "rw"));
        withUrlParam("autoReconnect", "true");
        withUrlParam("useUnicode", "true");
        withUrlParam("characterEncoding", "UTF-8");
        withUrlParam("allowMultiQueries", "true");
        withUrlParam("serverTimezone", "UTC");
        withUrlParam("useLegacyDatetimeCode", "false");
        withUrlParam("createDatabaseIfNotExist", "true");
        withUrlParam("rewriteBatchedStatements", "true");
        withDatabaseName("testDB");
        withUsername("root");
        withPassword("");
        withInitScript("db/changelog/sql/create_schemas.sql");
        withCommand("mysqld", "--lower_case_table_names=1", "--sql_mode=STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION");
        addFixedExposedPort(32804, 3306);
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.datasource.url", getJdbcUrl());
        System.setProperty("spring.datasource.username", getUsername());
        System.setProperty("spring.datasource.password", getPassword());

    }

    @Override
    public void stop(){
        // to ignore stop command after test class finished
    }

    public void shutdown(){
        super.stop();
    }
}
