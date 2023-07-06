package com.chatapp.usermanagement.config.security.client;


import com.chatapp.usermanagement.config.rest.RestProperties;
import com.chatapp.usermanagement.web.dto.UserDetailsTransfer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${security-service.name}")
public interface SecurityServerClient {
    final String BASE_URL = RestProperties.ROOT + "/v1" + RestProperties.AUTH.ROOT;

    @PostMapping(path = BASE_URL + RestProperties.AUTH.CHECK_TOKEN, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    UserDetailsTransfer checkToken(@RequestBody String token);
}
