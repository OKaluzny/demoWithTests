package com.example.demowithtests.util.mapper;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDtoRec;
import com.example.demowithtests.dto.EmployeeReadDtoRec;
import com.example.demowithtests.dto.EmployeeUpdateDtoRec;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Artem Kovalov on 14.06.2023
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeReadDtoRec toReadDto(Employee entity);
    EmployeeDtoRec toDto(Employee entity);
    @InheritInverseConfiguration
    Employee toEntity(EmployeeDtoRec dto);

    Employee updateDtoToEntity(EmployeeUpdateDtoRec dto);

    List<EmployeeReadDtoRec> listToReadDto(List<Employee> entityList);
    @InheritInverseConfiguration
    List<Employee> listToDomain(List<EmployeeDtoRec> entityList);
}
