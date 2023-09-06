package com.chatapp.usermanagement.event;

import com.chatapp.usermanagement.web.dto.UserDTO;
import org.springframework.context.ApplicationEvent;

public class UserUpdateEvent extends ApplicationEvent {


    public UserUpdateEvent(Object source) {
        super(source);
    }

    public UserDTO getUser() {
        return (UserDTO) super.getSource();
    }

}

