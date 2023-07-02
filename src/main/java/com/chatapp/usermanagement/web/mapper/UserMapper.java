package com.chatapp.usermanagement.web.mapper;


import com.chatapp.usermanagement.domain.User;
import com.chatapp.usermanagement.web.dto.RegistrationForm;
import com.chatapp.usermanagement.web.dto.UserDetailsTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "dateOfBirth", source = "dateOfBirth", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "roles", ignore = true)
    User registerFormToUser(RegistrationForm registrationForm);

    @Mapping(target = "authorities", expression = "java(map(user.getAuthorities()))")
    UserDetailsTransfer userToUserDetailsTransfer(User user);

    default Set<String> map(Set<GrantedAuthority> authorities) {
        if (authorities == null) {
            return null;
        }
        return authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }
}
