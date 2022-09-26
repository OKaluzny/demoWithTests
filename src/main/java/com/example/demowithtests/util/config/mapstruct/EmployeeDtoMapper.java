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
    Employee employeeAdminDtoToEmployee(EmployeeAdminDto employeeAdminDto);

    EmployeeDto createEmployeeDto(Employee employee);
    Employee employeeDtoToEmployee(EmployeeDto employeeDto);
    //EmployeeReadAdminDto readByIdAdminReadEmployeeDto (Employee employee);
    EmployeeReadDto readByIdEmployeeDto (Employee employee);

    List<EmployeeReadAllDto> getAllEmployeeDto (List<Employee> employeeList);

    List<EmployeeReadAllByNameDto> getAllByNameEmployeeDto (List<Employee> employeeList);

    List<EmployeeReadAllByCountryDto> getAllByCountryEmployeeDto (List<Employee> employeeList);

    List<EmployeeReadAllByGmailDto> getAllByGmailEmployeeDto (List<Employee> employeeList);

    List<EmployeeReadAllByIsAdultDto> getAllByIsAdultEmployeeDto (List<Employee> employeeList);

    List<EmployeeReadAllByIsDeletedDto> getAllByIsDeletedEmployeeDto (List<Employee> employeeList);

    EmployeeUpdateDto updateByIdEmployeeDto (Employee employee);

    EmployeeUpdateNameDto updateNameByIdEmployeeDto (Employee employee);

    EmployeeUpdateCountryDto updateCountryByIdEmployeeDto (Employee employee);

    EmployeeUpdateAgeDto updateAgeByIdEmployeeDto (Employee employee);

    EmployeeUpdateEmailDto updateEmailByIdEmployeeDto (Employee employee);

    EmployeeUpdateIsDeletedDto updateIsDeletedByIdEmployeeDto (Employee employee);
}
