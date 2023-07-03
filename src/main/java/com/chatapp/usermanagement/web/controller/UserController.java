package com.chatapp.usermanagement.web.controller;


import com.chatapp.usermanagement.annotation.LoginPerm;
import com.chatapp.usermanagement.annotation.ReadUserDetailsPerm;
import com.chatapp.usermanagement.annotation.RegisterPerm;
import com.chatapp.usermanagement.config.rest.RestProperties;
import com.chatapp.usermanagement.services.UserService;
import com.chatapp.usermanagement.web.dto.LoginForm;
import com.chatapp.usermanagement.web.dto.RegistrationForm;
import com.chatapp.usermanagement.web.dto.UserDetailsTransfer;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(RestProperties.ROOT + "/v1" + RestProperties.USER.ROOT)
public class UserController {

    private final UserService userService;

    private final String BASE_URL = RestProperties.ROOT + "/v1" + RestProperties.USER.ROOT;


    @Operation(summary = "Register a new user"
            , description = "Register a new user with the given details,provided by the security manager service"
            , tags = {"users"})
    @PostMapping(path = RestProperties.USER.REGISTER, consumes = "application/json", produces = "application/json")
    @RegisterPerm
    public ResponseEntity<UserDetailsTransfer> register(@RequestBody @Validated RegistrationForm registrationForm) {
        URI location = URI.create(String.format("%s/%s", BASE_URL, registrationForm.username()));
        return ResponseEntity.created(location).body(userService.register(registrationForm));
    }


    @GetMapping(path = "/{userId}" + RestProperties.USER.DETAILS, produces = "application/json")
    @ReadUserDetailsPerm
    public ResponseEntity<UserDetailsTransfer> getUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.loadUserByUsernameOrEmail(userId));
    }

    @LoginPerm
    @PostMapping(path = RestProperties.USER.LOGIN, produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserDetailsTransfer> login(@RequestBody LoginForm loginForm) {
        return ResponseEntity.ok(userService.login(loginForm));
    }
}
