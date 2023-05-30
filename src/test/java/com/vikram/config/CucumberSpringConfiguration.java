package com.vikram.config;

import com.vikram.Application;
import com.vikram.repository.UserEntityRepository;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = {Application.class, TestConfiguration.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CucumberSpringConfiguration {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Before
    public void clear() {
        userEntityRepository.deleteAll();
    }
}
