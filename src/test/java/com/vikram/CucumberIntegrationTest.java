package com.vikram;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = {
                "com.vikram.steps",
                "com.vikram.config"
        },
        plugin = {
                "pretty", "html:target/cucumber-html-report.html",
                "json:target/cucumber.json",
                "rerun:target/cucumber-api-rerun.txt"
        }
)
public class CucumberIntegrationTest {
}
