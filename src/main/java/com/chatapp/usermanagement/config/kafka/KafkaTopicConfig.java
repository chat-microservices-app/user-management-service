package com.chatapp.usermanagement.config.kafka;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaTopicConfig {
    @Value("${spring.kafka.topic.user-management-update}")
    private String userManagementUpdateTopic;

    @Value("${spring.kafka.topic.user-management-delete}")
    private String userManagementDeleteTopic;


    // setting up bean for topic name
    @Bean
    public NewTopic userManagementUpdateTopic() {
        return TopicBuilder.name(userManagementUpdateTopic)
                .build();
    }

    @Bean
    public NewTopic userManagementDeleteTopic() {
        return TopicBuilder.name(userManagementDeleteTopic)
                .build();
    }



}
