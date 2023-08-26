package com.chatapp.usermanagement.services;

import com.chatapp.usermanagement.web.dto.RegistrationForm;
import com.chatapp.usermanagement.web.dto.UserDTO;
import com.chatapp.usermanagement.web.dto.UserDetailsTransfer;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService extends JpaUserDetailsService {



    UserDTO getUserSession(String username);

    UserDetailsTransfer loadUserByUsernameOrEmail(String usernameOrEmail) throws UsernameNotFoundException;

    void updateProfilePicture(String userId, String pictureUrl);

    UserDTO updateUserProperties(String userId, RegistrationForm userDTO);

    UUID deleteUser(UUID userId);
}
