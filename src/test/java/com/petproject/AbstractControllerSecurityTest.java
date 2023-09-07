package com.petproject;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@ContextConfiguration(classes = {TestConfig.class})
@ComponentScan(useDefaultFilters = false, lazyInit = true)
@TestPropertySource(locations = "classpath:secret.test.properties")
public class AbstractControllerSecurityTest {
}
