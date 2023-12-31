package com.chatapp.usermanagement.config.security.filter;


import com.chatapp.usermanagement.config.rest.RestProperties;
import com.chatapp.usermanagement.config.security.SecurityManager;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * To invoke security manager to authenticate the token
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final SecurityManager securityManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token != null && !token.isBlank()) {
            // gets the jwt token from the header
            String tokenData = StringUtils.substringAfter(token, RestProperties.TOKEN_PREFIX);
            securityManager.authenticate(tokenData).ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        }
        filterChain.doFilter(request, response);


    }
}
