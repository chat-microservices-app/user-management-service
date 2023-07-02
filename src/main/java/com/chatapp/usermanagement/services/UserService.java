package com.chatapp.usermanagement.services;

import com.chatapp.usermanagement.web.dto.UserDetailsTransfer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends JpaUserDetailsService {

    UserDetailsTransfer loadUserByUsernameOrEmail(String usernameOrEmail) throws UsernameNotFoundException;
}
