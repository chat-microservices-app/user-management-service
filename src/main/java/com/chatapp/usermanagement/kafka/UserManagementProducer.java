package com.chatapp.usermanagement.kafka;

import com.chatapp.usermanagement.web.dto.UserDTO;

public interface UserManagementProducer {

    void updateUser(UserDTO userDTO);
}
