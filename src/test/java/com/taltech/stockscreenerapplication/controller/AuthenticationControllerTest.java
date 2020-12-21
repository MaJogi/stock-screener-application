package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.model.User;
import com.taltech.stockscreenerapplication.repository.UserRepository;
import com.taltech.stockscreenerapplication.util.payload.request.LoginRequest;
import com.taltech.stockscreenerapplication.util.payload.request.SignupRequest;
import com.taltech.stockscreenerapplication.util.payload.response.JwtResponse;
import com.taltech.stockscreenerapplication.util.payload.response.MessageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import java.net.URI;
import java.net.URISyntaxException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {

    @Autowired
    UserRepository userRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getRootUrl() {
        return "http://localhost:" + port + "/auth";
    }

    private String token = "";

    @Test
    void registerUserTest() throws URISyntaxException {
        SignupRequest signupRequest = new SignupRequest("testUsername", "test1", "test2", "qwerty");
        URI uri = new URI(getRootUrl() + "/signup");
        MessageResponse response = restTemplate.postForObject(uri, signupRequest, MessageResponse.class);

        assertThat(response.getMessage(), equalTo("User registered successfully!"));
    }

    @Test
    void takenUsernameTest() throws URISyntaxException {
        SignupRequest signupRequest = new SignupRequest("testUsername", "test1", "test2", "qwerty");
        URI uri = new URI(getRootUrl() + "/signup");
        MessageResponse response = restTemplate.postForObject(uri, signupRequest, MessageResponse.class);

        assertThat(response.getMessage(), equalTo("Username is already taken!"));
    }

    @Test
    void loginUserTest() throws URISyntaxException {
        LoginRequest loginRequest = new LoginRequest("testUsername", "qwerty");
        URI uri = new URI(getRootUrl() + "/login");
        JwtResponse jwt = restTemplate.postForObject(uri, loginRequest, JwtResponse.class);
        assertNotNull(jwt.getToken());

        userRepository.deleteById(jwt.getId());
    }
}
