package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

public class EmployeeMapper extends CustomMapper<Employee, EmployeeDto> {

    @Override
    public void mapBtoA(EmployeeDto dto, Employee entity, MappingContext context) {
        super.mapBtoA(dto, entity, context);
    }
}
