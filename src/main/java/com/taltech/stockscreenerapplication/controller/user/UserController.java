package com.taltech.stockscreenerapplication.controller.user;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.User;
import com.taltech.stockscreenerapplication.util.payload.request.AddTickerRequest;
import com.taltech.stockscreenerapplication.util.payload.response.MessageResponse;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import com.taltech.stockscreenerapplication.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyDimensionRepository companyDimensionRepository;

    private static final String USER_NOT_FOUND_MESSAGE = "Unable to find user by id: ";

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('USER')")
    public User getUser(@PathVariable final Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_MESSAGE + userId));
    }

    @PostMapping(value = "/{userId}/tickers", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> saveTicker(@PathVariable final Long userId,
                                                      @RequestBody final AddTickerRequest addTickerRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_MESSAGE + userId));
        CompanyDimension companyDimension = companyDimensionRepository.findById(addTickerRequest.getTickerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company by id: " + addTickerRequest.getTickerId()));

        Set<CompanyDimension> tickers = user.getTickers();
        tickers.add(companyDimension);
        userRepository.save(user);

        return ResponseEntity
                .status(201)
                .body(new MessageResponse("Ticker added successfully!"));
    }

    @DeleteMapping(value = "/{userId}/tickers/{tickerId}", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> deleteTicker(@PathVariable final Long userId,
                                                        @PathVariable final String tickerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_MESSAGE + userId));
        CompanyDimension companyDimension = companyDimensionRepository.findById(tickerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company by id: " + tickerId));

        Set<CompanyDimension> tickers = user.getTickers();
        if (!tickers.contains(companyDimension)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: cannot delete ticker that has not been added!"));
        }

        tickers.remove(companyDimension);
        userRepository.save(user);

        return ResponseEntity
                .ok(new MessageResponse("Ticker deleted successfully!"));
    }
}
