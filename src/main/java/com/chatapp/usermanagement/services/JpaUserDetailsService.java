package com.chatapp.usermanagement.services;

import com.chatapp.usermanagement.web.dto.LoginForm;
import com.chatapp.usermanagement.web.dto.RegistrationForm;

import com.chatapp.usermanagement.web.dto.UserDetailsTransfer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface JpaUserDetailsService extends UserDetailsService {

     UserDetailsTransfer register(RegistrationForm registrationForm);

     UserDetailsTransfer login(LoginForm loginForm);

}
