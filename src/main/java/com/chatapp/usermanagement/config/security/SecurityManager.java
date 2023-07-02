package com.chatapp.usermanagement.config.security;


import com.chatapp.usermanagement.config.security.client.SecurityServerClient;
import com.chatapp.usermanagement.web.dto.UserDetailsTransfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Component
@Log4j2
public class SecurityManager {


    private final SecurityServerClient securityServerClient;

    // this is the jwt which contains the user details and the roles
    public Optional<Authentication> authenticate(final String authToken) {
        return getAuthenticationInfo(authToken).map(this::getAuthentication);
    }

    private Optional<UserDetailsTransfer> getAuthenticationInfo(String authToken) {
        try {
            return Optional.of(securityServerClient.checkToken(authToken));
        } catch (Exception exception) {
            log.error("Error while getting user details from security service", exception);
            return Optional.empty();
        }
    }



    private UsernamePasswordAuthenticationToken getAuthentication(UserDetailsTransfer userDetailsTransfer) {
        Collection<? extends GrantedAuthority> authorities = ofNullable(userDetailsTransfer.getAuthorities())
                .map(auth ->
                        auth.stream()
                                .map(perm -> (GrantedAuthority) new SimpleGrantedAuthority(perm))
                                .collect(Collectors.toSet())
                )
                .orElseGet(Set::of);
        return new UsernamePasswordAuthenticationToken(userDetailsTransfer, null, authorities);
    }


}
