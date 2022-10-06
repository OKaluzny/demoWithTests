//package com.example.demowithtests.util.config.mapstruct;
//
//import com.example.demowithtests.domain.Employee;
//import com.example.demowithtests.dto.EmployeeToReadDto;
//import org.mapstruct.Mapper;
//import org.mapstruct.factory.Mappers;
//import org.springframework.data.domain.Page;
//
//@Mapper(componentModel = "spring")
//public interface PageDtoMapper {
//
//    PageDtoMapper PAGE_DTO_MAPPER = Mappers.getMapper(PageDtoMapper.class);
//
//    EmployeeToReadDto employeePageDto (Employee employee);
//
//    default Page<EmployeeToReadDto> employeePageDto(Page<Employee> employees) {
//        return employees.map(this::employeePageDto);
//    }
//
//}
