package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.createDto.EmployeeAdminDto;
import com.example.demowithtests.dto.createDto.EmployeeDto;
import com.example.demowithtests.dto.readDto.*;
import com.example.demowithtests.dto.updateDto.*;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeConverter {

    private final MapperFacade mapperFacade;

    public EmployeeConverter(MapperFacade mapperFacade) {
        this.mapperFacade = mapperFacade;
    }

    public MapperFacade getMapperFacade() {
        return mapperFacade;
    }

    public EmployeeDto toDto(Employee entity) {
        return mapperFacade.map(entity, EmployeeDto.class);
    }

    public EmployeeAdminDto toAdminDto(Employee entity){return mapperFacade.map(entity,EmployeeAdminDto.class);}

    public EmployeeReadDto toReadDto(Employee entity) {
        return mapperFacade.map(entity, EmployeeReadDto.class);
    }

    public EmployeeReadAdminDto toReadDtoAdmin(Employee entity) {
        return mapperFacade.map(entity, EmployeeReadAdminDto.class);
    }

    public List<EmployeeReadAllDto> toReadAllDto(List<Employee> entityList) {
        return mapperFacade.mapAsList(entityList, EmployeeReadAllDto.class);
    }

    public List<EmployeeReadAllByNameDto> toReadAllByNameDto(List<Employee> entityList) {
        return mapperFacade.mapAsList(entityList, EmployeeReadAllByNameDto.class);
    }

    public List<EmployeeReadAllByCountryDto> toReadAllByCountryDto(List<Employee> entityList) {
        return mapperFacade.mapAsList(entityList, EmployeeReadAllByCountryDto.class);
    }

    public List<EmployeeReadAllByGmailDto> toReadAllByGmailDto(List<Employee> entityList) {
        return mapperFacade.mapAsList(entityList, EmployeeReadAllByGmailDto.class);
    }

    public List<EmployeeReadAllByIsAdultDto> toReadAllByIsAdultDto(List<Employee> entityList) {
        return mapperFacade.mapAsList(entityList, EmployeeReadAllByIsAdultDto.class);
    }

    public List<EmployeeReadAllByIsDeletedDto> toReadAllByIsDeletedDto(List<Employee> entityList) {
        return mapperFacade.mapAsList(entityList, EmployeeReadAllByIsDeletedDto.class);
    }

    public EmployeeUpdateDto toUpdateDto (Employee entity) {
        return mapperFacade.map(entity, EmployeeUpdateDto.class);
    }

    public EmployeeUpdateIsDeleted toUpdateIsDeletedDto (Employee entity) {
        return mapperFacade.map(entity, EmployeeUpdateIsDeleted.class);
    }

    public EmployeeUpdateAgeDto toUpdateAgeDto (Employee entity) {
        return mapperFacade.map(entity, EmployeeUpdateAgeDto.class);
    }

    public EmployeeUpdateCountryDto toUpdateCountryDto (Employee entity) {
        return mapperFacade.map(entity, EmployeeUpdateCountryDto.class);
    }

    public EmployeeUpdateEmailDto toUpdateEmailDto (Employee entity) {
        return mapperFacade.map(entity, EmployeeUpdateEmailDto.class);
    }

    public Employee fromDto(EmployeeDto dto) {
        return mapperFacade.map(dto, Employee.class);
    }
}