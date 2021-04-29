package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.repository.user.UserRepository;
import com.taltech.stockscreenerapplication.util.payload.request.LoginRequest;
import com.taltech.stockscreenerapplication.util.payload.request.SignupRequest;
import com.taltech.stockscreenerapplication.util.payload.response.JwtResponse;
import com.taltech.stockscreenerapplication.util.payload.response.MessageResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationControllerTest {

    @Autowired
    UserRepository userRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getRootUrl() {
        return "http://localhost:" + port + "/auth";
    }

    private static JwtResponse jwt;

    @Test
    @Order(1)
    void registerUserTest() throws URISyntaxException {
        SignupRequest signupRequest = new SignupRequest("testUsername", "test1", "test2", "qwerty");
        URI uri = new URI(getRootUrl() + "/signup");
        MessageResponse response = restTemplate.postForObject(uri, signupRequest, MessageResponse.class);

        assertThat("The register user request response message is not what is expected.", response.getMessage(), equalTo("User registered successfully!"));
    }

    @Test
    @Order(2)
    void takenUsernameTest() throws URISyntaxException {
        SignupRequest signupRequest = new SignupRequest("testUsername", "test1", "test2", "qwerty");
        URI uri = new URI(getRootUrl() + "/signup");
        MessageResponse response = restTemplate.postForObject(uri, signupRequest, MessageResponse.class);

        assertThat("The register user request response message for taken username is not what is expected.", response.getMessage(), equalTo("Username is already taken!"));
    }

    @Test
    @Order(3)
    void incorrectLoginTest() throws URISyntaxException {
        LoginRequest loginRequest = new LoginRequest("testUsernameIncorrect", "qwerty");
        URI uri = new URI(getRootUrl() + "/login");
        MessageResponse response = restTemplate.postForObject(uri, loginRequest, MessageResponse.class);

        assertThat("The incorrect user login request response message is not what is expected.", response.getMessage(), equalTo("Error: Username and password combination was incorrect!"));
    }

    @Test
    @Order(4)
    void loginUserTest() throws URISyntaxException {
        LoginRequest loginRequest = new LoginRequest("testUsername", "qwerty");
        URI uri = new URI(getRootUrl() + "/login");
        jwt = restTemplate.postForObject(uri, loginRequest, JwtResponse.class);

        assertNotNull(jwt.getToken(), "The expected JWT token is not in the login request response.");
    }

    @Test
    @Order(5)
    void cleanUpUser() {
        userRepository.deleteById(jwt.getId());

        assertThat("The query to find user should return null.", userRepository.findById(jwt.getId()), equalTo(Optional.empty()));
    }
}
