package com.chatapp.usermanagement.web.controller;


import com.chatapp.usermanagement.annotation.LoginPerm;
import com.chatapp.usermanagement.annotation.ReadUserDetailsPerm;
import com.chatapp.usermanagement.annotation.RegisterPerm;
import com.chatapp.usermanagement.config.rest.RestProperties;
import com.chatapp.usermanagement.services.UserService;
import com.chatapp.usermanagement.web.dto.LoginForm;
import com.chatapp.usermanagement.web.dto.RegistrationForm;
import com.chatapp.usermanagement.web.dto.UserDTO;
import com.chatapp.usermanagement.web.dto.UserDetailsTransfer;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(RestProperties.ROOT + "/v1" + RestProperties.USER.ROOT)
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{username}", produces = "application/json")
    public ResponseEntity<UserDTO> getUserSession(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getUserSession(username));
    }

    @Operation(summary = "Register a new user"
            , description = "Register a new user with the given details,provided by the security service"
            , tags = {"users"})
    @PostMapping(path = RestProperties.USER.REGISTER, consumes = "application/json", produces = "application/json")
    @RegisterPerm
    public ResponseEntity<UserDetailsTransfer> register(@RequestBody @Validated RegistrationForm registrationForm) {
        String BASE_URL = RestProperties.ROOT + "/v1" + RestProperties.USER.ROOT;
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

    @PutMapping(path = "/{userId}/profile-picture", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Void> updateProfilePicture(@PathVariable("userId") String userId,
                                                     @RequestHeader HttpHeaders headers) {
        // the location header contains the url of the uploaded image
        userService.updateProfilePicture(
                userId,
                Objects.requireNonNull(headers.getLocation()).toString()
        );
        return ResponseEntity.noContent().build();
    }


    @PutMapping(path = "/{userId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") String userId,
                                              @RequestBody RegistrationForm updateForm) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.updateUserProperties(userId, updateForm));
    }

    @DeleteMapping(path = "/{userId}", produces = "application/json")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") UUID userId) {
        String BASE_URL = RestProperties.ROOT + "/v1" + RestProperties.USER.ROOT;
        URI uri = URI.create(String.format("%s%s", BASE_URL, userService.deleteUser(userId)));
        return ResponseEntity.noContent().location(uri).build();
    }
}
