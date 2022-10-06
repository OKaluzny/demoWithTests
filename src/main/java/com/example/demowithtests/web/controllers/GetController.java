package com.example.demowithtests.web.controllers;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.readDto.*;
import com.example.demowithtests.service.Service;
import com.example.demowithtests.util.config.mapstruct.EmployeeDtoMapper;
import com.example.demowithtests.web.interfaces.get.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Employee", description = "Employee API")
public class GetController implements GetAllRequest,
        GetByIdRequest,
        GetByNameRequest,
        GetByCountry,
        GetAdultRequest,
        GetGmailUsersRequest,
        GetHideRequest {
    private final Service service;
    private final EmployeeDtoMapper mapper;


    @Override
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    // A method that returns a list of employees.
    public List<EmployeeReadAllDto> getAllEmployeeDto() {
        return mapper.getAllEmployeeDto(service.getAll());
    }

    @GetMapping("/users/page")
    @ResponseStatus(HttpStatus.OK)
    // A method that returns a page of employees.
    public Page<Employee> getAllEmployeeByPage(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size,
                                               @RequestParam(defaultValue = "") List<String> sortList,
                                               @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder) {
        return service.findAllByPage(page, size, sortList, sortOrder.toString());
    }

    @Override
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    // This method returns a user by id.
    public EmployeeReadDto getByIdEmployeeDto(@PathVariable Integer id) {
        return mapper.readByIdEmployeeDto(service.getById(id));
    }

    @Override
    @GetMapping(value ="users", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    // This method returns a list of employees by name.
    public List<EmployeeReadAllByNameDto> getListByName(@RequestParam(value = "name") String name){
        return mapper.getAllByNameEmployeeDto(service.findUserByName(name));
    }

    @GetMapping(value ="/users/page", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    // This is a method that returns a page of employees by name.
    public Page<Employee> findByNamePage(@RequestParam(required = false) String name,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "3") int size,
                                         @RequestParam(defaultValue = "") List<String> sortList,
                                         @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        return service.findByName(name, page, size, sortList, sortOrder.toString());
    }

    @Override
    @GetMapping(value ="users", params = {"country"})
    @ResponseStatus(HttpStatus.OK)
    // This is a method that returns a list of employees by country.
    public List<EmployeeReadAllByCountryDto> getEmployeeByCountry(@RequestParam(value = "country") String country){
        return mapper.getAllByCountryEmployeeDto(service.findEmployeeByCountry(country));
    }

    @GetMapping(value ="/users/page", params = {"country"})
    @ResponseStatus(HttpStatus.OK)
    // This is a method that returns a page of employees by country.
    public Page<Employee> findByCountry(@RequestParam(required = false) String country,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "3") int size,
                                         @RequestParam(defaultValue = "") List<String> sortList,
                                         @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        return service.findByName(country, page, size, sortList, sortOrder.toString());
    }

    @Override
    @GetMapping(value ="users", params = {"isAdult"})
    @ResponseStatus(HttpStatus.OK)
    // This is a method that returns a list of employees by isAdult.
    public List<EmployeeReadAllByIsAdultDto> getAdultUsers(@RequestParam(value = "isAdult") Boolean isAdult) {
        return mapper.getAllByIsAdultEmployeeDto(service.findAdultUser(isAdult));
    }

    @Override
    @GetMapping(value ="users", params = {"email"})
    @ResponseStatus(HttpStatus.OK)
    // This is a method that returns a list of employees by email.
    public List<EmployeeReadAllByGmailDto> getGmailUser(@RequestParam(value = "email") String email) {
        return mapper.getAllByGmailEmployeeDto(service.findEmployeeByEmail(email));
    }

    @Override
    @GetMapping(value ="users", params = {"isdeleted"})
    @ResponseStatus(HttpStatus.OK)
    // This is a method that returns a list of employees by isDeleted.
    public List<EmployeeReadAllByIsDeletedDto> getUserByIsDeletedValue(@RequestParam(value = "isdeleted") Boolean isDeleted) {
        return mapper.getAllByIsDeletedEmployeeDto(service.findAllByIsDeleted(isDeleted));
    }

    @GetMapping(value ="users/countries")
    @ResponseStatus(HttpStatus.OK)
    // This is a method that returns a list of countries.
    public List<String> getEmployeeCountry() {
        return service.getEmployeeSortCountry();
    }

    @GetMapping(value ="users/email")
    @ResponseStatus(HttpStatus.OK)
    // This is a method that returns a list of emails.
    public Optional<String> getEmail() {
        return service.getEmail();
    }

    @GetMapping(value ="users/age")
    @ResponseStatus(HttpStatus.OK)
    // This is a method that returns a list of ages.
    public List<Integer> getAge() {
        return service.getAge();
    }

    @GetMapping(value ="users/ageAndEmail")
    @ResponseStatus(HttpStatus.OK)
    // This is a method that returns a list of employees by age and email.
    public Optional<Employee> getByAgeAndByEmail() {
        return service.getEmployeeByAgeAndByEmail();
    }
}
