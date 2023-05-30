package com.vikram.config;

import com.vikram.helper.TestContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

    @Bean
    public TestContext testContext() {
        return new TestContext();
    }
}
