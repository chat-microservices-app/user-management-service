package com.chatapp.usermanagement.config.kafka;


import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.serializer.JsonDeserializer;


@Configuration
public class KafkaTopicConfig {
    @Value("${spring.kafka.topic.name}")
    private String topicName;

    // setting up bean for topic name
    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topicName)
                .build();
    }



}
