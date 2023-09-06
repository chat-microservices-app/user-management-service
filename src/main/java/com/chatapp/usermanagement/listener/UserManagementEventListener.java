package com.chatapp.usermanagement.listener;


import com.chatapp.usermanagement.event.UserDeleteEvent;
import com.chatapp.usermanagement.event.UserUpdateEvent;
import com.chatapp.usermanagement.kafka.UserManagementProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class UserManagementEventListener {

    private final UserManagementProducer userManagementProducer;
    @Async
    @EventListener
    public synchronized void listen(UserUpdateEvent event) {
        log.debug("updating microservices");
        userManagementProducer.updateUser(event.getUser());
    }

    @Async
    @EventListener
    public synchronized void listen(UserDeleteEvent event) {
        log.debug("deleting microservices");
        userManagementProducer.deleteUser(event.getUser());
    }
}
