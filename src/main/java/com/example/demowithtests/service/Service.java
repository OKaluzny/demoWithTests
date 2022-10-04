package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.createDto.EmployeeDto;
import com.example.demowithtests.dto.readDto.*;
import com.example.demowithtests.dto.updateDto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface Service {

    Employee create(Employee employee);

    List<Employee> getAll();

    Employee getById(Integer id);

    Employee updateById(Integer id, Employee plane);

    void removeById(Integer id);

    void removeAll();

    List<Employee> findUserByName(String name);
    Page<Employee> findByName(String name, int page, int size, List<String> sortList, String sortOrder);

    List<Employee> findAdultUser(Boolean isAdult);

    List<Employee> findEmployeeByCountry(String country);
    Page<Employee> findByCountry(String country, int page, int size, List<String> sortList, String sortOrder);

    List<Employee> findEmployeeByEmail(String email);

    Employee hideEmployee(Integer id);

    List<Employee> findAllByIsDeleted(Boolean isDeleted);

    Employee updateNameById(Integer id, String name);

    Employee updateCountryById(Integer id, String country);
    Employee updateEmailById(Integer id, String email);
    Employee updateAgeById(Integer id, Integer age);


}