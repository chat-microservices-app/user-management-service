package com.chatapp.usermanagement.services;

import com.chatapp.usermanagement.web.dto.UserDTO;
import com.chatapp.usermanagement.web.dto.UserDetailsTransfer;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends JpaUserDetailsService {



    UserDTO getUserSession(String username);

    UserDetailsTransfer loadUserByUsernameOrEmail(String usernameOrEmail) throws UsernameNotFoundException;
}
