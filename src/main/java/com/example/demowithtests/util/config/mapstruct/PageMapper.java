//package com.example.demowithtests.util.config.mapstruct;
//
//import com.example.demowithtests.domain.Employee;
//import com.example.demowithtests.dto.EmployeeToReadDto;
//import org.mapstruct.Mapper;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PageMapper implements PageDto {
//
//    private final EmployeeMapper employeeMapper;
//
//    public PageMapper(EmployeeMapper employeeMapper) {
//        this.employeeMapper = employeeMapper;
//    }
//
//    @Override
//    public Page<EmployeeToReadDto> employeeGmail(Page<Employee> employeePage) {
//        if (employeePage == null) {
//            return null;
//        }
//
//        Page<EmployeeToReadDto> pageDto = employeePage.map(c -> {
//            return employeeMapper.employeeToReadDto(c);
//        });
//        return pageDto;
//    }
//}