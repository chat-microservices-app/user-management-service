package com.chatapp.usermanagement.web.mapper;


import com.chatapp.usermanagement.domain.User;
import com.chatapp.usermanagement.web.dto.RegistrationForm;
import com.chatapp.usermanagement.web.dto.UserDTO;
import com.chatapp.usermanagement.web.dto.UserDetailsTransfer;
import org.mapstruct.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "dateOfBirth", source = "dateOfBirth", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "roles", ignore = true)
    User registerFormToUser(RegistrationForm registrationForm);

    @Mapping(target = "authorities", expression = "java(map(user.getAuthorities()))")
    UserDetailsTransfer userToUserDetailsTransfer(User user);

    UserDTO userToUserDTO(User user);


    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "username", ignore = true)
    void updateUserFromRegistrationForm(RegistrationForm updateForm, @MappingTarget User user);

    default Set<String> map(Set<GrantedAuthority> authorities) {
        if (authorities == null) {
            return null;
        }
        return authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

    // condition that check if all attribute are not null; in the case of string it checks if it is not empty
    // or full of spaces
    @Condition
    default boolean isNotNull(Object attribute) {
        if (attribute instanceof String) {
            return !((String) attribute).trim().isEmpty();
        }
        return attribute != null;
    }
}
