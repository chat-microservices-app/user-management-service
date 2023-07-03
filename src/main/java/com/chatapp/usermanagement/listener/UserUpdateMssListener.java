package com.chatapp.usermanagement.listener;


import com.chatapp.usermanagement.event.UserUpdateMssEvent;
import com.chatapp.usermanagement.kafka.UserManagementProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class UserUpdateMssListener {

    private final UserManagementProducer userManagementProducer;
    @Async
    @EventListener
    public synchronized void listen(UserUpdateMssEvent event) {
        log.debug("updating microservices");
        userManagementProducer.updateUser(event.getUser());
    }
}
