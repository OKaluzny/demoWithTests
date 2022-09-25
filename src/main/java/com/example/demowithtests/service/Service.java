package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.createDto.EmployeeDto;
import com.example.demowithtests.dto.readDto.*;
import com.example.demowithtests.dto.updateDto.*;

import java.util.List;

public interface Service {

    EmployeeDto create(Employee employee);

    List<EmployeeReadAllDto> getAll();

    EmployeeReadDto getById(Integer id);

    EmployeeUpdateDto updateById(Integer id, Employee plane);

    void removeById(Integer id);

    void removeAll();

    List<EmployeeReadAllByNameDto> findUserByName(String name);

    List<EmployeeReadAllByIsAdultDto> findAdultUser(Boolean isAdult);

    List<EmployeeReadAllByCountryDto> findEmployeeByCountry(String country);

    List<EmployeeReadAllByGmailDto> findEmployeeByEmail(String email);

    EmployeeUpdateIsDeletedDto hideEmployee(Integer id);

    List<EmployeeReadAllByIsDeletedDto> findAllByIsDeleted(Boolean isDeleted);

    EmployeeUpdateNameDto updateNameById(Integer id, String name);

    EmployeeUpdateCountryDto updateCountryById(Integer id, String country);
    EmployeeUpdateEmailDto updateEmailById(Integer id, String email);
    EmployeeUpdateAgeDto updateAgeById(Integer id, Integer age);


}