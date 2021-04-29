package com.taltech.stockscreenerapplication.controller.user;

import com.taltech.stockscreenerapplication.model.ERole;
import com.taltech.stockscreenerapplication.model.User;
import com.taltech.stockscreenerapplication.model.UserRole;
import com.taltech.stockscreenerapplication.util.payload.request.LoginRequest;
import com.taltech.stockscreenerapplication.util.payload.request.SignupRequest;
import com.taltech.stockscreenerapplication.util.payload.response.JwtResponse;
import com.taltech.stockscreenerapplication.util.payload.response.MessageResponse;
import com.taltech.stockscreenerapplication.repository.user.UserRepository;
import com.taltech.stockscreenerapplication.repository.user.UserRoleRepository;
import com.taltech.stockscreenerapplication.security.jwt.JwtUtils;
import com.taltech.stockscreenerapplication.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    /* default */ AuthenticationManager authenticationManager;

    @Autowired
    /* default */ UserRepository userRepository;

    @Autowired
    /* default */ UserRoleRepository userRoleRepository;

    @Autowired
    /* default */ PasswordEncoder encoder;

    @Autowired
    /* default */ JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody final LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.createJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUserId(),
                userDetails.getUsername(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                roles,
                userDetails.getCompanyDimensions()
                ));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody final SignupRequest signUpRequest) {
        Boolean doesUserExistByUsername = userRepository.existsByUsername(signUpRequest.getUsername());
        if (Boolean.TRUE.equals(doesUserExistByUsername)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username is already taken!"));
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                encoder.encode(signUpRequest.getPassword()));

        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = userRoleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        userRoles.add(userRole);

        user.setRoles(userRoles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
