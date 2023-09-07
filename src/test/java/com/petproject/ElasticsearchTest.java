package com.petproject;

import org.testcontainers.junit.jupiter.Container;

interface ElasticsearchTest {

    @Container
    ElasticsearchContainerRule ELASTICSEARCH_CONTAINER = ElasticsearchContainerRule.getInstance();

}
