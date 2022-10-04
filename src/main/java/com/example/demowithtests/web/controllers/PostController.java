package com.example.demowithtests.web.controllers;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.createDto.EmployeeAdminDto;
import com.example.demowithtests.dto.createDto.EmployeeDto;
import com.example.demowithtests.service.Service;
import com.example.demowithtests.util.config.mapstruct.EmployeeDtoMapper;
import com.example.demowithtests.web.interfaces.post.PostAdminRequest;
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
public class PostController implements PostRequest, PostAdminRequest {

    private final Service service;
    private final EmployeeDtoMapper mapper;

    @Override
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    // A method that is called when a POST request is made to the `/api/users` endpoint.
    public EmployeeDto saveEmployee(@RequestBody Employee employee) {
        return mapper.createEmployeeDto(service.create(employee));
    }

    @Override
    @PostMapping("/users/admin")
    @ResponseStatus(HttpStatus.CREATED)
    // A method that is called when a POST request is made to the `/api/users/admin` endpoint.
    public EmployeeAdminDto saveAdminEmployee(@RequestBody Employee employee) {
        return mapper.createAdminEmployeeDto(service.create(employee));
    }
}
