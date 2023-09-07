package com.petproject;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(properties = {"spring.flyway.enabled=false"})
@ComponentScan(lazyInit = true)
@ContextConfiguration(classes = TestApplication.class)
@TestPropertySource(locations = "classpath:secret.test.properties", properties = {
    "spring.main.allow-bean-definition-overriding=true", "spring.jpa.show-sql=true"
})
@Testcontainers(disabledWithoutDocker = true)
public abstract class AbstractDataJpaTest implements DatabaseTest, ElasticsearchTest {

}
