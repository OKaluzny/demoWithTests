package com.example.demowithtests.util.mappers;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toEmployeeDto(Employee employee);

    EmployeeReadDto toEmployeeReadDto(Employee employee);

    List<EmployeeDto> toListEmployeeDto(List<Employee> employees);

    Employee toEmployee(EmployeeDto employeeDto);
}