package com.vikram.steps;

import com.google.gson.Gson;
import com.jayway.jsonassert.JsonAssert;
import com.jayway.jsonassert.JsonAsserter;
import com.vikram.controller.dto.GreetResponse;
import com.vikram.controller.dto.TokenResponse;
import com.vikram.helper.TestContext;
import io.cucumber.java.en.Then;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class UserAssertionSteps {

    private final TestContext testContext;

    public UserAssertionSteps(TestContext testContext) {

        this.testContext = testContext;
    }

    @Then("a user is registered successfully with response")
    public void userRegisteredSuccesfully(List<Map<String, String>> assertions) {
        ResponseEntity<TokenResponse> tokenResponseResponseEntity =
                (ResponseEntity<TokenResponse>) testContext.getFromContext(TestContext.TOKEN_RESPONSE);

        validatePropertyMatches(assertions, tokenResponseResponseEntity);
    }

    @Then("user is greeted with their details in response")
    public void userGreetedWithDetails(List<Map<String, String>> assertions) {
        ResponseEntity<GreetResponse> userAccountResponseEntity =
                (ResponseEntity<GreetResponse>) testContext.getFromContext(TestContext.GREET_RESPONSE);

        validatePropertyMatches(assertions, userAccountResponseEntity);
    }

    @Then("token is generated with response")
    public void tokenGeneratedSuccessfully(List<Map<String, String>> assertions) {
        ResponseEntity<TokenResponse> tokenResponseResponseEntity =
                (ResponseEntity<TokenResponse>) testContext.getFromContext(TestContext.TOKEN_RESPONSE);

        validatePropertyMatches(assertions, tokenResponseResponseEntity);
    }

    private void validatePropertyMatches(List<Map<String, String>> assertions, ResponseEntity tokenResponseResponseEntity) {
        String json = new Gson().toJson(tokenResponseResponseEntity);
        JsonAsserter asserter = JsonAssert.with(json);
        for (Map<String, String> a : assertions) {
            String path = a.get("property");
            String matcher = a.get("matcher");
            String expected = a.get("expected");

            switch (matcher) {
                case "isInt" -> asserter.assertThat(path, is(Integer.valueOf(expected)));
                case "isBoolean" -> asserter.assertThat(path, is(Boolean.valueOf(expected)));
                case "isNotDefined" -> asserter.assertNotDefined(path);
                case "isEmpty" -> asserter.assertThat(path, is(StringUtils.EMPTY));
                case "notNull" -> asserter.assertThat(path, is(notNullValue()));
                case "contains" -> asserter.assertThat(path, hasKey(expected));
                default -> asserter.assertThat(path, is(expected));
            }
        }
    }
}
