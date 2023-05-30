package com.vikram.steps;

import com.google.gson.Gson;
import com.vikram.controller.dto.UserAccount;
import com.vikram.helper.TestContext;
import io.cucumber.java.en.Given;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserArtifactSteps {

    private final TestContext testContext;

    public UserArtifactSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("a new user with details")
    public void givenUserDetails(String data) {
        UserAccount userAccount = new Gson().fromJson(data, UserAccount.class);
        testContext.addToContext(TestContext.USER_ACCOUNT, userAccount);
    }

    @Given("a user with login details")
    public void userWithLoginDetails(String data) {
        UserAccount userAccount = new Gson().fromJson(data, UserAccount.class);
        testContext.addToContext(TestContext.USER_ACCOUNT, userAccount);
    }
}
