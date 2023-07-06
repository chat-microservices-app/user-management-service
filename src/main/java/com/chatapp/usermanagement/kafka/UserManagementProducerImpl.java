package com.chatapp.usermanagement.kafka;

import com.chatapp.usermanagement.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserManagementProducerImpl implements UserManagementProducer {


    private final KafkaTemplate<String, UserDTO> kafkaTemplate;
    private final NewTopic topic;
    @Override
    public void updateUser(UserDTO userDTO) {
        Message<UserDTO> message = MessageBuilder
                .withPayload(userDTO)
                .setHeader(KafkaHeaders.TOPIC, topic.name())

                .build();
        kafkaTemplate.send(message);
    }
}
