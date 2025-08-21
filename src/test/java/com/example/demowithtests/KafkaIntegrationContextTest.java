package com.example.demowithtests;

import com.example.demowithtests.config.KafkaConfig;
import com.example.demowithtests.service.kafka.EmployeeEventConsumer;
import com.example.demowithtests.service.kafka.EmployeeEventProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@EmbeddedKafka(partitions = 1,
        topics = {KafkaConfig.EMPLOYEE_CREATED_TOPIC,
                KafkaConfig.EMPLOYEE_UPDATED_TOPIC,
                KafkaConfig.EMPLOYEE_DELETED_TOPIC})
@TestPropertySource(properties = {
    "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
    "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
    "spring.kafka.consumer.bootstrap-servers=${spring.embedded.kafka.brokers}"
})
class KafkaIntegrationContextTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        System.out.println("[DEBUG_LOG] Testing Spring Boot context loading with Kafka integration");
        assertNotNull(applicationContext, "Application context should not be null");
        System.out.println("[DEBUG_LOG] Application context loaded successfully");
    }

    @Test
    void kafkaConfigurationBeansAreLoaded() {
        System.out.println("[DEBUG_LOG] Testing Kafka configuration beans");

        // Verify KafkaConfig bean is loaded
        KafkaConfig kafkaConfig = applicationContext.getBean(KafkaConfig.class);
        assertNotNull(kafkaConfig, "KafkaConfig bean should be loaded");
        System.out.println("[DEBUG_LOG] KafkaConfig bean loaded successfully");

        // Verify EmployeeEventProducer bean is loaded
        EmployeeEventProducer producer = applicationContext.getBean(EmployeeEventProducer.class);
        assertNotNull(producer, "EmployeeEventProducer bean should be loaded");
        System.out.println("[DEBUG_LOG] EmployeeEventProducer bean loaded successfully");

        // Verify EmployeeEventConsumer bean is loaded
        EmployeeEventConsumer consumer = applicationContext.getBean(EmployeeEventConsumer.class);
        assertNotNull(consumer, "EmployeeEventConsumer bean should be loaded");
        System.out.println("[DEBUG_LOG] EmployeeEventConsumer bean loaded successfully");

        System.out.println("[DEBUG_LOG] All Kafka beans loaded successfully");
    }

    @Test
    void kafkaTopicsAreConfigured() {
        System.out.println("[DEBUG_LOG] Testing Kafka topic configuration");

        // Verify topic beans are configured
        assertNotNull(applicationContext.getBean("employeeCreatedTopic"), 
                     "Employee created topic should be configured");
        assertNotNull(applicationContext.getBean("employeeUpdatedTopic"), 
                     "Employee updated topic should be configured");
        assertNotNull(applicationContext.getBean("employeeDeletedTopic"), 
                     "Employee deleted topic should be configured");

        System.out.println("[DEBUG_LOG] All Kafka topics configured successfully");
    }
}
