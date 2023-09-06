package com.chatapp.usermanagement.kafka;

import com.chatapp.usermanagement.web.dto.UserDTO;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
public class UserManagementProducerImpl implements UserManagementProducer {


    private final KafkaTemplate<String, UserDTO> kafkaTemplate;
    private final NewTopic updateUserTopic;
    private final NewTopic deleteUserTopic;


    public UserManagementProducerImpl(KafkaTemplate<String, UserDTO> kafkaTemplate,
                                      @Qualifier("userManagementUpdateTopic") NewTopic updateUserTopic,
                                      @Qualifier("userManagementDeleteTopic") NewTopic deleteUserTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.updateUserTopic = updateUserTopic;
        this.deleteUserTopic = deleteUserTopic;
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        Message<UserDTO> message = MessageBuilder
                .withPayload(userDTO)
                .setHeader(KafkaHeaders.TOPIC, updateUserTopic.name())
                .build();
        kafkaTemplate.send(message);
    }

    @Override
    public void deleteUser(UserDTO userDTO) {
        Message<UserDTO> message = MessageBuilder
                .withPayload(userDTO)
                .setHeader(KafkaHeaders.TOPIC, deleteUserTopic.name())
                .build();
        kafkaTemplate.send(message);
    }
}
