package com.example.demowithtests.service.kafka;

import com.example.demowithtests.config.KafkaConfig;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.dto.EmployeeEvent;
import com.example.demowithtests.service.EmployeeService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 3, 
               topics = {KafkaConfig.EMPLOYEE_CREATED_TOPIC, 
                        KafkaConfig.EMPLOYEE_UPDATED_TOPIC, 
                        KafkaConfig.EMPLOYEE_DELETED_TOPIC})
@TestPropertySource(properties = {
    "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
    "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
    "spring.kafka.consumer.bootstrap-servers=${spring.embedded.kafka.brokers}"
})
class EmployeeKafkaIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    private BlockingQueue<ConsumerRecord<String, EmployeeEvent>> records;
    private KafkaMessageListenerContainer<String, EmployeeEvent> container;

    @BeforeEach
    void setUp() {
        records = new LinkedBlockingQueue<>();

        Map<String, Object> consumerProperties = Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getProperty("spring.embedded.kafka.brokers", "localhost:9092"),
            ConsumerConfig.GROUP_ID_CONFIG, "test-group",
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
            JsonDeserializer.TRUSTED_PACKAGES, "com.example.demowithtests.dto",
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
        );

        DefaultKafkaConsumerFactory<String, EmployeeEvent> consumerFactory = 
            new DefaultKafkaConsumerFactory<>(consumerProperties);

        ContainerProperties containerProperties = new ContainerProperties(
            KafkaConfig.EMPLOYEE_CREATED_TOPIC,
            KafkaConfig.EMPLOYEE_UPDATED_TOPIC,
            KafkaConfig.EMPLOYEE_DELETED_TOPIC
        );

        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        container.setupMessageListener((MessageListener<String, EmployeeEvent>) records::add);
        container.start();

        // Wait for the container to be assigned to partitions
        try {
            Thread.sleep(2000); // Give some time for assignment
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    void testEmployeeCreatedEventIsSent() throws InterruptedException {
        System.out.println("[DEBUG_LOG] Starting testEmployeeCreatedEventIsSent");

        // Given
        Employee employee = Employee.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .country("USA")
                .gender(Gender.M)
                .build();

        // When
        Employee createdEmployee = employeeService.create(employee);
        System.out.println("[DEBUG_LOG] Created employee with ID: " + createdEmployee.getId());

        // Then
        ConsumerRecord<String, EmployeeEvent> record = records.poll(10, TimeUnit.SECONDS);
        assertNotNull(record, "Should receive a Kafka message");

        EmployeeEvent event = record.value();
        assertNotNull(event);
        assertEquals(EmployeeEvent.EventType.CREATED, event.getEventType());
        assertEquals(createdEmployee.getId(), event.getEmployeeId());
        assertEquals("John Doe", event.getEmployeeName());
        assertEquals("john.doe@example.com", event.getEmployeeEmail());
        assertEquals("USA", event.getEmployeeCountry());
        assertNotNull(event.getCurrentData());
        assertNull(event.getPreviousData());

        System.out.println("[DEBUG_LOG] Successfully verified employee created event");
    }

    @Test
    void testEmployeeUpdatedEventIsSent() throws InterruptedException {
        System.out.println("[DEBUG_LOG] Starting testEmployeeUpdatedEventIsSent");

        // Given - create an employee first
        Employee employee = Employee.builder()
                .name("Jane Smith")
                .email("jane.smith@example.com")
                .country("Canada")
                .gender(Gender.F)
                .build();

        Employee createdEmployee = employeeService.create(employee);

        // Clear the created event from the queue
        records.poll(5, TimeUnit.SECONDS);

        // When - update the employee
        Employee updatedEmployee = Employee.builder()
                .name("Jane Johnson")
                .email("jane.johnson@example.com")
                .country("UK")
                .build();

        Employee result = employeeService.updateById(createdEmployee.getId(), updatedEmployee);
        System.out.println("[DEBUG_LOG] Updated employee with ID: " + result.getId());

        // Then
        ConsumerRecord<String, EmployeeEvent> record = records.poll(10, TimeUnit.SECONDS);
        assertNotNull(record, "Should receive a Kafka message");

        EmployeeEvent event = record.value();
        assertNotNull(event);
        assertEquals(EmployeeEvent.EventType.UPDATED, event.getEventType());
        assertEquals(result.getId(), event.getEmployeeId());
        assertEquals("Jane Johnson", event.getEmployeeName());
        assertEquals("jane.johnson@example.com", event.getEmployeeEmail());
        assertEquals("UK", event.getEmployeeCountry());

        // Verify previous and current data
        assertNotNull(event.getPreviousData());
        assertNotNull(event.getCurrentData());
        assertEquals("Jane Smith", event.getPreviousData().getName());
        assertEquals("Jane Johnson", event.getCurrentData().getName());

        System.out.println("[DEBUG_LOG] Successfully verified employee updated event");
    }

    @Test
    void testEmployeeDeletedEventIsSent() throws InterruptedException {
        System.out.println("[DEBUG_LOG] Starting testEmployeeDeletedEventIsSent");

        // Given - create an employee first
        Employee employee = Employee.builder()
                .name("Bob Wilson")
                .email("bob.wilson@example.com")
                .country("Australia")
                .gender(Gender.M)
                .build();

        Employee createdEmployee = employeeService.create(employee);

        // Clear the created event from the queue
        records.poll(5, TimeUnit.SECONDS);

        // When - delete the employee
        employeeService.removeById(createdEmployee.getId());
        System.out.println("[DEBUG_LOG] Deleted employee with ID: " + createdEmployee.getId());

        // Then
        ConsumerRecord<String, EmployeeEvent> record = records.poll(10, TimeUnit.SECONDS);
        assertNotNull(record, "Should receive a Kafka message");

        EmployeeEvent event = record.value();
        assertNotNull(event);
        assertEquals(EmployeeEvent.EventType.DELETED, event.getEventType());
        assertEquals(createdEmployee.getId(), event.getEmployeeId());
        assertEquals("Bob Wilson", event.getEmployeeName());
        assertEquals("bob.wilson@example.com", event.getEmployeeEmail());
        assertEquals("Australia", event.getEmployeeCountry());
        assertNotNull(event.getPreviousData());
        assertNull(event.getCurrentData());

        System.out.println("[DEBUG_LOG] Successfully verified employee deleted event");
    }
}
