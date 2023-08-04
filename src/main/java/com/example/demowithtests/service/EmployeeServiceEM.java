package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;

import java.util.Set;

public interface EmployeeServiceEM {

    Employee createWithJpa(Employee employee);

    void deleteByIdWithJpa(Integer id);

    Employee updateByIdWithJpa(Integer id, Employee employee);

    Set<String> findAllCountriesWithJpa();
}
