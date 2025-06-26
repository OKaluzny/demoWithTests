package com.example.demowithtests.util.mappers;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.AddressDto;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DocumentMapper.class})
public interface EmployeeMapper {

    @Mapping(target = "startDate", expression = "java(new java.util.Date())")
    EmployeeDto toEmployeeDto(Employee employee);

    @Mapping(target = "date", expression = "java(new java.util.Date())")
    EmployeeReadDto toEmployeeReadDto(Employee employee);

    List<EmployeeDto> toListEmployeeDto(List<Employee> employees);

    @Mapping(target = "document", ignore = true)
    Employee toEmployee(EmployeeDto employeeDto);

    @Mapping(target = "date", expression = "java(new java.util.Date())")
    AddressDto toAddressDto(Address address);
}
