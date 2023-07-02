package com.chatapp.usermanagement.config.security.client;


import com.chatapp.usermanagement.web.dto.UserDetailsTransfer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "security-service")
public interface SecurityServerClient {
    final String BASE_URL = "/api/v1/auth";

    @PostMapping(path = BASE_URL + "/check-token", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    UserDetailsTransfer checkToken(@RequestBody String token);
}
