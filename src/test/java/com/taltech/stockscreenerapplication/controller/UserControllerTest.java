package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.model.User;
import com.taltech.stockscreenerapplication.repository.user.UserRepository;
import com.taltech.stockscreenerapplication.util.payload.request.AddTickerRequest;
import com.taltech.stockscreenerapplication.util.payload.request.LoginRequest;
import com.taltech.stockscreenerapplication.util.payload.request.SignupRequest;
import com.taltech.stockscreenerapplication.util.payload.response.JwtResponse;
import com.taltech.stockscreenerapplication.util.payload.response.MessageResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    UserRepository userRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getRootUrl() {
        return "http://localhost:" + port + "/users";
    }

    private static JwtResponse jwt;
    private static Long userId;
    private static String authHeader;
    private String tickerId = "TKM1T";

    @Test
    @Order(1)
    void getUserTest() throws URISyntaxException {
        prepareUser();

        userId = jwt.getId();
        authHeader = "Bearer " + jwt.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<User> response = restTemplate.exchange(getRootUrl() + "/" + userId, HttpMethod.GET, entity, User.class);

        assertThat("The request response status is not 200.", response.getStatusCode(), equalTo(HttpStatus.OK));
}

    @Test
    @Order(2)
    void addUserTickerTest() {
        userId = jwt.getId();
        authHeader = "Bearer " + jwt.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        headers.setContentType(MediaType.APPLICATION_JSON);
        AddTickerRequest addTickerRequest = new AddTickerRequest(tickerId);
        HttpEntity<AddTickerRequest> entity = new HttpEntity<>(addTickerRequest, headers);
        ResponseEntity<MessageResponse> response = restTemplate.exchange(getRootUrl() + "/" + userId + "/tickers", HttpMethod.POST, entity, MessageResponse.class);

        assertThat("The add ticker to user request response message is not what is expected.", response.getBody().getMessage(), equalTo("Ticker added successfully!"));
    }

    @Test
    @Order(3)
    void deleteUserTickerTest() {
        userId = jwt.getId();
        authHeader = "Bearer " + jwt.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<MessageResponse> response = restTemplate.exchange(getRootUrl() + "/" + userId + "/tickers/" + tickerId, HttpMethod.DELETE, entity, MessageResponse.class);

        assertThat("The delete ticker from user request response message is not what is expected.", response.getBody().getMessage(), equalTo("Ticker deleted successfully!"));
    }

    @Test
    @Order(4)
    void deleteNotAssignedUserTickerTest() {
        userId = jwt.getId();
        authHeader = "Bearer " + jwt.getToken();
        String notAssignedTickerId = "CPA1T";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<MessageResponse> response = restTemplate.exchange(getRootUrl() + "/" + userId + "/tickers/" + notAssignedTickerId, HttpMethod.DELETE, entity, MessageResponse.class);

        assertThat("The incorrect user ticker deletion request response message is not what is expected.", response.getBody().getMessage(), equalTo("Error: cannot delete ticker that has not been added!"));
    }

    @Test
    @Order(5)
    void deleteNotExistingUserTickerTest() {
        userId = jwt.getId();
        authHeader = "Bearer " + jwt.getToken();
        String notExistingTickerId = "aaaa";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<MessageResponse> response = restTemplate.exchange(getRootUrl() + "/" + userId + "/tickers/" + notExistingTickerId, HttpMethod.DELETE, entity, MessageResponse.class);

        assertThat("The non-existing ticker deletion response message is not what is expected.", response.getBody().getMessage(), equalTo("Unable to find company by id: " + notExistingTickerId));
    }

    @Test
    @Order(6)
    void getNotExistingUserTest() {
        userId = jwt.getId();
        authHeader = "Bearer " + jwt.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<User> response = restTemplate.exchange(getRootUrl() + "/999999", HttpMethod.GET, entity, User.class);

        assertThat("The response status for non-existing user is not what is expected.", response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    @Order(7)
    void cleanUpUser() {
        userRepository.deleteById(jwt.getId());

        assertThat("The query to find user should return null.", userRepository.findById(jwt.getId()), equalTo(Optional.empty()));
    }

    void prepareUser() throws URISyntaxException {
        SignupRequest signupRequest = new SignupRequest("testUsername", "test1", "test2", "qwerty");
        URI uriSignup = new URI("http://localhost:" + port + "/auth/signup");
        restTemplate.postForObject(uriSignup, signupRequest, MessageResponse.class);

        LoginRequest loginRequest = new LoginRequest("testUsername", "qwerty");
        URI uriLogin = new URI("http://localhost:" + port + "/auth/login");
        jwt = restTemplate.postForObject(uriLogin, loginRequest, JwtResponse.class);
    }
}
