package com.example.demowithtests.web.controllers;

import com.example.demowithtests.dto.updateDto.*;
import com.example.demowithtests.service.Service;

import com.example.demowithtests.util.config.mapstruct.EmployeeDtoMapper;
import com.example.demowithtests.web.interfaces.patch.*;
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
public class PatchController implements PatchHideRequest, PatchUpdateCountryRequest, PatchUpdateEmailRequest, PatchUpdateNameRequest, PatchUpdateAgeRequest {

    private final Service service;
    private final EmployeeDtoMapper mapper;

    @Override
    @PatchMapping ("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    // A method that hides the employee by id.
    public EmployeeUpdateIsDeletedDto hideAdminEmployee(@PathVariable("id") Integer id) {
        return mapper.updateIsDeletedByIdEmployeeDto(service.hideEmployee(id));
    }

    //Изменить имя по id
    @Override
    @PatchMapping ("/users/name/{id}")
    @ResponseStatus(HttpStatus.OK)
    // This is a method that updates the name of the employee by id.
    public EmployeeUpdateNameDto updateNameById(@PathVariable("id") Integer id,@RequestParam(value = "name") String name) {
        return mapper.updateNameByIdEmployeeDto(service.updateNameById(id, name));
    }

    //Изменить страну по id
    @Override
    @PatchMapping ("/users/country/{id}")
    @ResponseStatus(HttpStatus.OK)
    // This is a method that updates the country of the employee by id.
    public EmployeeUpdateCountryDto updateCountryById(@PathVariable("id") Integer id, @RequestParam(value = "country") String country) {
        return mapper.updateCountryByIdEmployeeDto(service.updateCountryById(id, country));
    }

    //Изменить почту по id
    @Override
    @PatchMapping ("/users/email/{id}")
    @ResponseStatus(HttpStatus.OK)
    // This is a method that updates the email of the employee by id.
    public EmployeeUpdateEmailDto updateEmailById(@PathVariable("id") Integer id, @RequestParam(value = "email") String email) {
        return mapper.updateEmailByIdEmployeeDto(service.updateEmailById(id, email));
    }

    @Override
    @PatchMapping ("/users/age/{id}")
    // This is a method that updates the age of the employee by id.
    @ResponseStatus(HttpStatus.OK)
    public EmployeeUpdateAgeDto updateAgeById(@PathVariable("id") Integer id, @RequestParam(value = "age") Integer age) {
        return mapper.updateAgeByIdEmployeeDto(service.updateAgeById(id, age));
    }
}
