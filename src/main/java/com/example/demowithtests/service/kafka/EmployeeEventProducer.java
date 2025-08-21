package com.example.demowithtests.service.kafka;

import com.example.demowithtests.config.KafkaConfig;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeEventProducer implements EmployeeEventPublisher {

    private final KafkaTemplate<String, EmployeeEvent> kafkaTemplate;

    public void sendEmployeeCreatedEvent(Employee employee) {
        EmployeeEvent event = EmployeeEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(EmployeeEvent.EventType.CREATED)
                .timestamp(LocalDateTime.now())
                .employeeId(employee.getId())
                .employeeName(employee.getName())
                .employeeEmail(employee.getEmail())
                .employeeCountry(employee.getCountry())
                .currentData(EmployeeEvent.EmployeeData.builder()
                        .name(employee.getName())
                        .email(employee.getEmail())
                        .country(employee.getCountry())
                        .build())
                .build();

        sendEvent(KafkaConfig.EMPLOYEE_CREATED_TOPIC, event);
    }

    public void sendEmployeeUpdatedEvent(Employee previousEmployee, Employee currentEmployee) {
        EmployeeEvent event = EmployeeEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(EmployeeEvent.EventType.UPDATED)
                .timestamp(LocalDateTime.now())
                .employeeId(currentEmployee.getId())
                .employeeName(currentEmployee.getName())
                .employeeEmail(currentEmployee.getEmail())
                .employeeCountry(currentEmployee.getCountry())
                .previousData(EmployeeEvent.EmployeeData.builder()
                        .name(previousEmployee.getName())
                        .email(previousEmployee.getEmail())
                        .country(previousEmployee.getCountry())
                        .build())
                .currentData(EmployeeEvent.EmployeeData.builder()
                        .name(currentEmployee.getName())
                        .email(currentEmployee.getEmail())
                        .country(currentEmployee.getCountry())
                        .build())
                .build();

        sendEvent(KafkaConfig.EMPLOYEE_UPDATED_TOPIC, event);
    }

    public void sendEmployeeDeletedEvent(Employee employee) {
        EmployeeEvent event = EmployeeEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(EmployeeEvent.EventType.DELETED)
                .timestamp(LocalDateTime.now())
                .employeeId(employee.getId())
                .employeeName(employee.getName())
                .employeeEmail(employee.getEmail())
                .employeeCountry(employee.getCountry())
                .previousData(EmployeeEvent.EmployeeData.builder()
                        .name(employee.getName())
                        .email(employee.getEmail())
                        .country(employee.getCountry())
                        .build())
                .build();

        sendEvent(KafkaConfig.EMPLOYEE_DELETED_TOPIC, event);
    }

    private void sendEvent(String topic, EmployeeEvent event) {
        try {
            CompletableFuture<SendResult<String, EmployeeEvent>> future = 
                kafkaTemplate.send(topic, event.getEmployeeId().toString(), event);

            future.whenComplete((result, exception) -> {
                if (exception == null) {
                    log.info("Successfully sent employee event: {} to topic: {} with offset: {}",
                            event.getEventType(), topic, result.getRecordMetadata().offset());
                } else {
                    log.error("Failed to send employee event: {} to topic: {}", 
                            event.getEventType(), topic, exception);
                }
            });
        } catch (Exception e) {
            log.error("Error sending employee event to Kafka: {}", e.getMessage(), e);
        }
    }
}
