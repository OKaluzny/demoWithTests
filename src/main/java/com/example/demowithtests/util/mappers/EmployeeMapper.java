package com.example.demowithtests.util.mappers;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toEmployee(Employee employee);

    EmployeeReadDto toEmployeeRead(Employee employee);

    List<EmployeeDto> toEmployeeList(List<Employee> employees);

    Employee toEmployeeEntity(EmployeeDto employeeDto);
}