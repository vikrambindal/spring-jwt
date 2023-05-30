package com.vikram.steps;

import com.vikram.config.AppRestClient;
import com.vikram.controller.dto.TokenResponse;
import com.vikram.controller.dto.UserAccount;
import com.vikram.helper.TestContext;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;

public class UserActionSteps {

    private AppRestClient appRestClient;
    private final TestContext testContext;

    public UserActionSteps(AppRestClient appRestClient, TestContext testContext) {
        this.appRestClient = appRestClient;
        this.testContext = testContext;
    }

    @When("a user registers in the application")
    public void userRegistersInApplication() {
        UserAccount userAccount = (UserAccount) testContext.getFromContext(TestContext.USER_ACCOUNT_REQ);
        ResponseEntity<TokenResponse> tokenResponseResponseEntity = appRestClient.createUser(userAccount);
        testContext.addToContext(TestContext.TOKEN_RESPONSE, tokenResponseResponseEntity);
    }

    @When("a user generates a token")
    public void userGeneratesToken() {
        UserAccount userAccount = (UserAccount) testContext.getFromContext(TestContext.USER_ACCOUNT_REQ);
        ResponseEntity<TokenResponse> tokenResponseResponseEntity = appRestClient.generateToken(userAccount);
        testContext.addToContext(TestContext.TOKEN_RESPONSE, tokenResponseResponseEntity);
    }
}
