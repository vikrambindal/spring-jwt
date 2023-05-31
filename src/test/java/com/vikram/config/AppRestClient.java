package com.vikram.config;

import com.vikram.controller.dto.GreetResponse;
import com.vikram.controller.dto.TokenResponse;
import com.vikram.controller.dto.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Slf4j
@Scope(SCOPE_CUCUMBER_GLUE)
public class AppRestClient {

    private static final String SERVER_URL = "http://localhost";

    private static final String MANAGEMENT_PATH = "account";
    private static final String REGISTER_ENDPOINT = "register";
    private static final String GENERATE_TOKEN_ENDPOINT = "generate";
    private static final String GREET_ENDPOINT = "greet";
    private static final String APP_API_VERSION = "v1";

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    private URI getBaseURI() {
        return URI.create(String.format(SERVER_URL + ":%s", port));
    }

    private URI getRegisterEndpoint() {
        return UriComponentsBuilder.fromUri(getBaseURI())
                .pathSegment(MANAGEMENT_PATH, APP_API_VERSION, REGISTER_ENDPOINT)
                .build().toUri();
    }

    private URI getGenerateEndpoint() {
        return UriComponentsBuilder.fromUri(getBaseURI())
                .pathSegment(MANAGEMENT_PATH, APP_API_VERSION, GENERATE_TOKEN_ENDPOINT)
                .build().toUri();
    }

    private URI getGreetEndpoint() {
        return UriComponentsBuilder.fromUri(getBaseURI())
                .pathSegment(APP_API_VERSION, GREET_ENDPOINT)
                .build().toUri();
    }

    public ResponseEntity<TokenResponse> createUser(UserAccount userAccount) {
        HttpEntity<UserAccount> userAccountHttpEntity = new HttpEntity<>(userAccount);
        return restTemplate.exchange(getRegisterEndpoint(),
                HttpMethod.POST, userAccountHttpEntity, TokenResponse.class);
    }

    public ResponseEntity<TokenResponse> generateToken(UserAccount userAccount) {
        HttpEntity<UserAccount> userAccountHttpEntity = new HttpEntity<>(userAccount);
        try {
            return restTemplate.exchange(getGenerateEndpoint(),
                    HttpMethod.POST, userAccountHttpEntity, TokenResponse.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        }
    }

    public ResponseEntity<GreetResponse> generateToken(String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                getGreetEndpoint(), HttpMethod.GET, requestEntity, GreetResponse.class);
    }
}
