package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.createDto.EmployeeDto;
import com.example.demowithtests.dto.readDto.*;
import com.example.demowithtests.dto.updateDto.EmployeeUpdateAgeDto;
import com.example.demowithtests.dto.updateDto.EmployeeUpdateCountryDto;
import com.example.demowithtests.dto.updateDto.EmployeeUpdateDto;
import com.example.demowithtests.dto.updateDto.EmployeeUpdateEmailDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

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

    public EmployeeReadDto toReadDto(Employee entity) {
        return mapperFacade.map(entity, EmployeeReadDto.class);
    }

    public EmployeeReadAdminDto toReadDtoAdmin(Employee entity) {
        return mapperFacade.map(entity, EmployeeReadAdminDto.class);
    }

    public EmployeeReadAllByNameDto toReadAllByName(Employee entity) {
        return mapperFacade.map(entity, EmployeeReadAllByNameDto.class);
    }

    public EmployeeReadAllByCountryDto toReadAllByCountry(Employee entity) {
        return mapperFacade.map(entity, EmployeeReadAllByCountryDto.class);
    }

    public EmployeeReadAllByGmailDto toReadAllByGmail(Employee entity) {
        return mapperFacade.map(entity, EmployeeReadAllByGmailDto.class);
    }

    public EmployeeUpdateDto toUpdateDto (Employee entity) {
        return mapperFacade.map(entity, EmployeeUpdateDto.class);
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