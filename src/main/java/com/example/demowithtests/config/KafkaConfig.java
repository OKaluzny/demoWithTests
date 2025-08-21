package com.example.demowithtests.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    public static final String EMPLOYEE_CREATED_TOPIC = "employee-created";
    public static final String EMPLOYEE_UPDATED_TOPIC = "employee-updated";
    public static final String EMPLOYEE_DELETED_TOPIC = "employee-deleted";

    @Bean
    public NewTopic employeeCreatedTopic() {
        return TopicBuilder.name(EMPLOYEE_CREATED_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic employeeUpdatedTopic() {
        return TopicBuilder.name(EMPLOYEE_UPDATED_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic employeeDeletedTopic() {
        return TopicBuilder.name(EMPLOYEE_DELETED_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}