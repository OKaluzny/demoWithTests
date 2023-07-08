package com.example.demowithtests.util.mapper;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeePassport;
import com.example.demowithtests.domain.EmployeePhoto;
import com.example.demowithtests.dto.employee.*;
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

    EmployeeReadDto toReadDto(Employee entity);
    EmployeeDto toDto(Employee entity);
    @InheritInverseConfiguration
    Employee toEntity(EmployeeDto dto);

    Employee updateDtoToEntity(EmployeeUpdateDto dto);

    List<EmployeeReadDto> listToReadDto(List<Employee> entityList);
    @InheritInverseConfiguration
    List<Employee> listToDomain(List<EmployeeDto> entityList);

    EmployeePassportDto passportToDto(EmployeePassport passport);
    EmployeePassport passportToEntity(EmployeePassportDto passportDto);

    EmployeePhotoDto photoToDto(EmployeePhoto photo);
    EmployeePhoto photoToEntity(EmployeePhotoDto photoDto);
}
