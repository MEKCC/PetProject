package com.petproject;

import org.testcontainers.elasticsearch.ElasticsearchContainer;

public class ElasticsearchContainerRule extends ElasticsearchContainer {
    private static final String IMAGE_VERSION = "docker.elastic.co/elasticsearch/elasticsearch:7.8.1";

    private static volatile ElasticsearchContainerRule instance;

    public static ElasticsearchContainerRule getInstance() {
        if (instance == null) {
            instance = new ElasticsearchContainerRule();
        }
        return instance;
    }

    public ElasticsearchContainerRule() {
        super(IMAGE_VERSION);
        withReuse(true);
        withEnv("discovery.type", "single-node");
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.elasticsearch.rest.uris", getHttpHostAddress());
    }

    @Override
    public void stop() {
        // to ignore stop command after test class finished
    }

    public void shutdown() {
        super.stop();
    }
}
