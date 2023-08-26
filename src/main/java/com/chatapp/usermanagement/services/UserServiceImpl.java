package com.chatapp.usermanagement.services;

import com.chatapp.usermanagement.domain.User;
import com.chatapp.usermanagement.event.UserUpdateMssEvent;
import com.chatapp.usermanagement.exceptions.PasswordNotMatchException;
import com.chatapp.usermanagement.repositories.UserRepository;
import com.chatapp.usermanagement.utils.RoleHandler;
import com.chatapp.usermanagement.web.dto.LoginForm;
import com.chatapp.usermanagement.web.dto.RegistrationForm;
import com.chatapp.usermanagement.web.dto.UserDTO;
import com.chatapp.usermanagement.web.dto.UserDetailsTransfer;
import com.chatapp.usermanagement.web.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;


@RequiredArgsConstructor
@Log4j2
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final RoleService roleService;

    private final AuthorityService authorityService;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public UserDetailsTransfer register(RegistrationForm registrationForm) {


        if (userRepository.existsByUsername(registrationForm.username())) {
            throw new IllegalArgumentException("username already exists");
        }
        // filer out roles by prefixing ROLE_ and save it
        String roles = RoleHandler.getRoles(registrationForm.roles());

        // save all authorities
        String permissions = RoleHandler.getPermissions(registrationForm.roles());

        User user = userMapper.registerFormToUser(registrationForm);
        user.setRoles(roleService.saveAllRoles(roles, authorityService.saveAllAuthorities(permissions)));

        log.debug("User to be saved: {}", user.getRoles());
        User savedUser = userRepository.saveAndFlush(user);

        // publish event to update other microservices to populate user data
        applicationEventPublisher.publishEvent(new UserUpdateMssEvent(userMapper.userToUserDTO(savedUser)));

        return userMapper.userToUserDetailsTransfer(userRepository.save(savedUser));
    }


    @Override
    public UserDetailsTransfer login(LoginForm loginForm) {
        User userToLoggedIn = (User) loadUserByUsername(loginForm.usernameOrEmail());
        if (Objects.equals(userToLoggedIn.getPassword(), loginForm.password())) {
            return userMapper.userToUserDetailsTransfer(userToLoggedIn);
        }
        throw new PasswordNotMatchException("username or password is not correct");
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
                () -> new UsernameNotFoundException("username or email is not part of our records")
        );
    }

    @Override
    public UserDTO getUserSession(String username) {
        User user = userRepository.findByUsername(username, User.class).orElseThrow(
                () -> new UsernameNotFoundException("user not found with username : " + username)
        );
        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDetailsTransfer loadUserByUsernameOrEmail(String usernameOrEmail) throws UsernameNotFoundException {
        return userMapper.userToUserDetailsTransfer((User) loadUserByUsername(usernameOrEmail));
    }

    @Override
    public void updateProfilePicture(String userId, String pictureUrl) {
        User user = userRepository.findByUsername(userId, User.class).orElseThrow(
                () -> new UsernameNotFoundException("username or email is not part of our records")
        );
        user.setPictureUrl(pictureUrl);
        User savedUser = userRepository.saveAndFlush(user);

        // publish event to update other microservices to populate user data
        applicationEventPublisher.publishEvent(new UserUpdateMssEvent(userMapper.userToUserDTO(savedUser)));

    }

    @Override
    public UserDTO updateUserProperties(String userId, RegistrationForm userDTO) {
        // check if the user exists
        User user = userRepository.findByUsername(userId, User.class).orElseThrow(
                () -> new UsernameNotFoundException("username or email or email is not part of our records")
        );
        userMapper.updateUserFromRegistrationForm(userDTO, user);

        User savedUser = userRepository.saveAndFlush(user);

        // publish event to update other microservices to populate user data
        applicationEventPublisher.publishEvent(new UserUpdateMssEvent(userMapper.userToUserDTO(savedUser)));

        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public UUID deleteUser(UUID userId) {
        if(!userRepository.existsById(userId)){
            throw new UsernameNotFoundException(" The user is not found");
        }
        //TODO delete all the user data in other microservices
        // includes chat-service, and media-service
        userRepository.deleteById(userId);
        return userId;
    }
}
