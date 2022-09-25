package com.example.demowithtests.util.config.mapstruct;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.createDto.EmployeeAdminDto;
import com.example.demowithtests.dto.createDto.EmployeeDto;
import com.example.demowithtests.dto.readDto.*;
import com.example.demowithtests.dto.updateDto.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeDtoMapper {
    EmployeeAdminDto createAdminEmployeeDto(Employee employee);
    EmployeeDto createEmployeeDto(Employee employee);
    //EmployeeReadAdminDto readByIdAdminReadEmployeeDto (Employee employee);
    EmployeeReadDto readByIdEmployeeDto (Employee employee);
    EmployeeReadAllDto getAllEmployeeDto (List<Employee> employeeList);
    EmployeeReadAllByNameDto getAllByNameEmployeeDto (List<Employee> employeeList);
    EmployeeReadAllByCountryDto getAllByCountryEmployeeDto (List<Employee> employeeList);
    EmployeeReadAllByGmailDto getAllByGmailEmployeeDto (List<Employee> employeeList);
    EmployeeReadAllByIsAdultDto getAllByIsAdultEmployeeDto (List<Employee> employeeList);
    EmployeeReadAllByIsDeletedDto getAllByIsDeletedEmployeeDto (List<Employee> employeeList);
    EmployeeUpdateDto updateByIdEmployeeDto (Employee employee);
    EmployeeUpdateNameDto updateNameByIdEmployeeDto (Employee employee);
    EmployeeUpdateCountryDto updateCountryByIdEmployeeDto (Employee employee);
    EmployeeUpdateAgeDto updateAgeByIdEmployeeDto (Employee employee);
    EmployeeUpdateEmailDto updateEmailByIdEmployeeDto (Employee employee);
    EmployeeUpdateIsDeletedDto updateIsDeletedByIdEmployeeDto (Employee employee);
}
