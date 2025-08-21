package com.example.demowithtests.service.kafka;

import com.example.demowithtests.domain.Employee;

public interface EmployeeEventPublisher {
    void sendEmployeeCreatedEvent(Employee employee);
    void sendEmployeeUpdatedEvent(Employee previousEmployee, Employee currentEmployee);
    void sendEmployeeDeletedEvent(Employee employee);
}