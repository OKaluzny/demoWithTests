package com.example.demowithtests.util.config.mapstruct;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeCreateDto;
import com.example.demowithtests.dto.EmployeeReadTechDto;
import com.example.demowithtests.dto.EmployeeToReadDto;
import com.example.demowithtests.dto.EmployeeUpdateDto;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeToReadDto employeeToReadDto(Employee employee);

    List<EmployeeToReadDto> toDtoList(List<Employee> employeeList);

    EmployeeCreateDto createDto(Employee employee);

    Employee createEmployeeFromDto(EmployeeCreateDto employeeCreateDto);

    EmployeeUpdateDto updatePhoneNumberDto(Employee employee);

    List<EmployeeReadTechDto> getTechDto(List<Employee> employee);

    List<EmployeeToReadDto> readByCountry(List<Employee> employeeList);

    EmployeeUpdateDto updateEmployeeDto(Employee employee);

    Employee updateEmployeeFromDto(EmployeeUpdateDto employeeUpdateDto);

}
