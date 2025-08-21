package com.example.demowithtests.service.kafka;

import com.example.demowithtests.config.KafkaConfig;
import com.example.demowithtests.dto.EmployeeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmployeeEventConsumer {

    @KafkaListener(topics = KafkaConfig.EMPLOYEE_CREATED_TOPIC, groupId = "employee-service-group")
    public void handleEmployeeCreatedEvent(
            @Payload EmployeeEvent event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment) {
        
        try {
            log.info("Received EMPLOYEE_CREATED event: eventId={}, employeeId={}, employeeName={}, topic={}, partition={}, offset={}",
                    event.getEventId(), event.getEmployeeId(), event.getEmployeeName(), topic, partition, offset);
            
            // Process the employee created event
            processEmployeeCreatedEvent(event);
            
            // Acknowledge the message
            acknowledgment.acknowledge();
            
        } catch (Exception e) {
            log.error("Error processing employee created event: {}", e.getMessage(), e);
            // In a real application, you might want to send to a dead letter queue
        }
    }

    @KafkaListener(topics = KafkaConfig.EMPLOYEE_UPDATED_TOPIC, groupId = "employee-service-group")
    public void handleEmployeeUpdatedEvent(
            @Payload EmployeeEvent event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment) {
        
        try {
            log.info("Received EMPLOYEE_UPDATED event: eventId={}, employeeId={}, employeeName={}, topic={}, partition={}, offset={}",
                    event.getEventId(), event.getEmployeeId(), event.getEmployeeName(), topic, partition, offset);
            
            // Process the employee updated event
            processEmployeeUpdatedEvent(event);
            
            // Acknowledge the message
            acknowledgment.acknowledge();
            
        } catch (Exception e) {
            log.error("Error processing employee updated event: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = KafkaConfig.EMPLOYEE_DELETED_TOPIC, groupId = "employee-service-group")
    public void handleEmployeeDeletedEvent(
            @Payload EmployeeEvent event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment) {
        
        try {
            log.info("Received EMPLOYEE_DELETED event: eventId={}, employeeId={}, employeeName={}, topic={}, partition={}, offset={}",
                    event.getEventId(), event.getEmployeeId(), event.getEmployeeName(), topic, partition, offset);
            
            // Process the employee deleted event
            processEmployeeDeletedEvent(event);
            
            // Acknowledge the message
            acknowledgment.acknowledge();
            
        } catch (Exception e) {
            log.error("Error processing employee deleted event: {}", e.getMessage(), e);
        }
    }

    private void processEmployeeCreatedEvent(EmployeeEvent event) {
        log.info("Processing employee created event for employee: {} (ID: {})", 
                event.getEmployeeName(), event.getEmployeeId());
        
        // Here you could implement business logic such as:
        // - Sending welcome emails
        // - Creating user accounts in other systems
        // - Updating analytics/reporting systems
        // - Triggering workflows
        
        log.info("Employee created event processed successfully for employee: {}", event.getEmployeeName());
    }

    private void processEmployeeUpdatedEvent(EmployeeEvent event) {
        log.info("Processing employee updated event for employee: {} (ID: {})", 
                event.getEmployeeName(), event.getEmployeeId());
        
        if (event.getPreviousData() != null && event.getCurrentData() != null) {
            log.info("Employee data changed from: {} to: {}", 
                    event.getPreviousData(), event.getCurrentData());
        }
        
        // Here you could implement business logic such as:
        // - Updating external systems
        // - Sending notification emails about changes
        // - Auditing changes
        // - Synchronizing with other services
        
        log.info("Employee updated event processed successfully for employee: {}", event.getEmployeeName());
    }

    private void processEmployeeDeletedEvent(EmployeeEvent event) {
        log.info("Processing employee deleted event for employee: {} (ID: {})", 
                event.getEmployeeName(), event.getEmployeeId());
        
        // Here you could implement business logic such as:
        // - Cleaning up related data in other systems
        // - Sending farewell emails
        // - Archiving employee data
        // - Updating reporting systems
        
        log.info("Employee deleted event processed successfully for employee: {}", event.getEmployeeName());
    }
}