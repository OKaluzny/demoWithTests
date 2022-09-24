package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.updateDto.EmployeeUpdateNameDto;

import java.util.List;

public interface Service {

    Employee create(Employee employee);

    List<Employee> getAll();

    Employee getById(Integer id);

    Employee updateById(Integer id, Employee plane);

    void removeById(Integer id);

    void removeAll();

    List<Employee> findUserByName(String name);

    List<Employee> findAdultUser(Boolean isAdult);

    List<Employee> findEmployeeByCountry(String country);

    List<Employee> findEmployeeByEmail(String email);

    Employee hideEmployee(Integer id);

    List<Employee> findAllByIsDeleted(Boolean isDeleted);

    Employee updateNameById(Integer id, String name);
}