package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeCreateDto;
import com.example.demowithtests.dto.EmployeeToReadDto;
import com.example.demowithtests.dto.EmployeeUpdateDto;

import java.util.List;

public interface Service {

    Employee create(Employee employee);

    List<Employee> getAll();

    Employee getById(Integer id);

    Employee updateById(Integer id, Employee plane);

    void removeById(Integer id);

    void removeAll();

    List<Employee> findAllByName(String name);

    List<Employee> findUsersWithPhoneNumber();

    List<Employee> findRecordsWhereEmailNull();

    List<Employee> findEmployeesByCountry(String country);

    Employee updatePhoneById (Integer id, Integer phoneNumber);

}
