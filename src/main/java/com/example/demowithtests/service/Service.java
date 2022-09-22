package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;

import java.util.List;

public interface Service {

    Employee create(Employee employee);

    List<Employee> getAll();

    Employee getById(Integer id);

    Employee updateById(Integer id, Employee plane);

    void removeById(Integer id);

    void removeAll();

    void isDeleted(Integer id);

    List<Employee> getAllUsers();

    List<Employee> getName(String name);

    List<Employee> isAccess(Integer id);

    List<Employee> getListCountry(String country);

    void updateEmail(Integer id, String email);


//    void updateHour(Integer id, Double hour);

//    void getSalary(Integer id);
//
//    List<Object> salaryInfo();
}
