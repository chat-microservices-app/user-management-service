package com.chatapp.usermanagement.event;

import com.chatapp.usermanagement.web.dto.UserDTO;
import org.springframework.context.ApplicationEvent;

public class UserUpdateMssEvent extends ApplicationEvent {


    public UserUpdateMssEvent(Object source) {
        super(source);
    }

    public UserDTO getUser() {
        return (UserDTO) super.getSource();
    }

}

