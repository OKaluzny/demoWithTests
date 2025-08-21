package com.example.demowithtests.service.kafka;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.service.EmployeeServiceBean;
import com.example.demowithtests.service.emailSevice.EmailSenderService;
import com.example.demowithtests.service.history.HistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeKafkaServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private HistoryService historyService;

    @Mock
    private EmployeeEventPublisher employeeEventProducer;

    @InjectMocks
    private EmployeeServiceBean employeeService;

    @Test
    void testCreateEmployeeSendsKafkaEvent() {
        System.out.println("[DEBUG_LOG] Starting testCreateEmployeeSendsKafkaEvent");

        // Given
        Employee employee = Employee.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .country("USA")
                .gender(Gender.M)
                .build();

        Employee savedEmployee = Employee.builder()
                .id(1)
                .name("John Doe")
                .email("john.doe@example.com")
                .country("USA")
                .gender(Gender.M)
                .build();

        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

        // When
        Employee result = employeeService.create(employee);

        // Then
        verify(employeeRepository).save(employee);
        verify(employeeEventProducer).sendEmployeeCreatedEvent(savedEmployee);

        System.out.println("[DEBUG_LOG] Successfully verified employee created event was sent");
    }

    @Test
    void testUpdateEmployeeSendsKafkaEvent() {
        System.out.println("[DEBUG_LOG] Starting testUpdateEmployeeSendsKafkaEvent");

        // Given
        Integer employeeId = 1;
        Employee existingEmployee = Employee.builder()
                .id(employeeId)
                .name("Jane Smith")
                .email("jane.smith@example.com")
                .country("Canada")
                .gender(Gender.F)
                .build();

        Employee updateData = Employee.builder()
                .name("Jane Johnson")
                .email("jane.johnson@example.com")
                .country("UK")
                .build();

        Employee updatedEmployee = Employee.builder()
                .id(employeeId)
                .name("Jane Johnson")
                .email("jane.johnson@example.com")
                .country("UK")
                .gender(Gender.F)
                .build();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        // When
        Employee result = employeeService.updateById(employeeId, updateData);

        // Then
        verify(employeeRepository).findById(employeeId);
        verify(employeeRepository).save(any(Employee.class));
        verify(employeeEventProducer).sendEmployeeUpdatedEvent(any(Employee.class), eq(updatedEmployee));

        System.out.println("[DEBUG_LOG] Successfully verified employee updated event was sent");
    }

    @Test
    void testDeleteEmployeeSendsKafkaEvent() {
        System.out.println("[DEBUG_LOG] Starting testDeleteEmployeeSendsKafkaEvent");

        // Given
        Integer employeeId = 1;
        Employee employee = Employee.builder()
                .id(employeeId)
                .name("Bob Wilson")
                .email("bob.wilson@example.com")
                .country("Australia")
                .gender(Gender.M)
                .build();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // When
        employeeService.removeById(employeeId);

        // Then
        verify(employeeRepository).findById(employeeId);
        verify(employeeEventProducer).sendEmployeeDeletedEvent(employee);
        verify(employeeRepository).delete(employee);

        System.out.println("[DEBUG_LOG] Successfully verified employee deleted event was sent");
    }

    @Test
    void testCreateEmployeeHandlesKafkaException() {
        System.out.println("[DEBUG_LOG] Starting testCreateEmployeeHandlesKafkaException");

        // Given
        Employee employee = Employee.builder()
                .name("Test User")
                .email("test@example.com")
                .country("Test Country")
                .gender(Gender.M)
                .build();

        Employee savedEmployee = Employee.builder()
                .id(1)
                .name("Test User")
                .email("test@example.com")
                .country("Test Country")
                .gender(Gender.M)
                .build();

        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);
        doThrow(new RuntimeException("Kafka error")).when(employeeEventProducer).sendEmployeeCreatedEvent(any(Employee.class));

        // When
        Employee result = employeeService.create(employee);

        // Then - should still return the saved employee even if Kafka fails
        verify(employeeRepository).save(employee);
        verify(employeeEventProducer).sendEmployeeCreatedEvent(savedEmployee);

        System.out.println("[DEBUG_LOG] Successfully verified employee creation continues even when Kafka fails");
    }
}
