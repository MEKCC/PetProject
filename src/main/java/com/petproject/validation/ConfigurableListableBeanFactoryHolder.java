package com.petproject.validation;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class ConfigurableListableBeanFactoryHolder {

    private static ConfigurableListableBeanFactory factory;

    public ConfigurableListableBeanFactoryHolder(final ConfigurableListableBeanFactory factory) {
        ConfigurableListableBeanFactoryHolder.factory = factory;
    }

    public static ConfigurableListableBeanFactory getInstance() {
        return factory;
    }

}
