package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.old_dto.EmployeeDto;
import dev.akkinoc.spring.boot.orika.OrikaMapperFactoryConfigurer;
import ma.glasnost.orika.MapperFactory;

public class MappingConfig implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory mapperFactory) {

        mapperFactory.classMap(Employee.class, EmployeeDto.class)
                .customize(new EmployeeMapperOld())
                .byDefault()
                .register();

        mapperFactory.classMap(Employee.class, EmployeeDto.class)
                .byDefault()
                .register();
    }
}
