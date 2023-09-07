package com.petproject;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.junit5.api.DBRider;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.testcontainers.junit.jupiter.Container;

@DBRider
@DBUnit(
    metaDataHandler = MySqlMetadataHandler.class,
    qualifiedTableNames = true,
    allowEmptyFields = true,
    batchedStatements = true,
    disableSequenceFiltering = true
)
interface DatabaseTest {

    @Container
    MySQLContainerRule MYSQL_CONTAINER = MySQLContainerRule.getInstance();
}
