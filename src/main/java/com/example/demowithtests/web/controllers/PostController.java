package com.example.demowithtests.web.controllers;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.createDto.EmployeeDto;
import com.example.demowithtests.service.Service;
import com.example.demowithtests.util.config.EmployeeConverter;
import com.example.demowithtests.web.interfaces.post.PostRequest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Employee", description = "Employee API")
public class PostController implements PostRequest {

    private final Service service;
    private final EmployeeConverter converter;

    //Операция сохранения юзера в базу данных
    @Override
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto saveEmployee(EmployeeDto requestForSave) {
        var employee = converter.getMapperFacade().map(requestForSave, Employee.class);
        var dto = converter.toDto(service.create(employee));

        return dto;
    }
}
