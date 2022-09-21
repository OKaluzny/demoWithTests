package com.example.demowithtests.web.controllers;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.readDto.EmployeeReadDto;
import com.example.demowithtests.service.Service;
import com.example.demowithtests.util.config.EmployeeConverter;
import com.example.demowithtests.web.interfaces.get.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private final EmployeeConverter converter;

    //Получение списка юзеров
    @Override
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllUsers() {
        return service.getAll();
    }

    //Получения юзера по id
    @Override
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        log.debug("getEmployeeById() Controller - start: id = {}", id);
        var employee = service.getById(id);
        log.debug("getById() Controller - to dto start: id = {}", id);
        var dto = converter.toReadDto(employee);
        log.debug("getEmployeeById() Controller - end: name = {}", dto.name);
        return dto;
    }

    //Получение юзеров по имени
    @Override
    @GetMapping(value ="users", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getListByName(@RequestParam(value = "name") String name){
        return service.findUserByName(name);
    }

    //Получение юзеров по стране
    @Override
    @GetMapping(value ="users", params = {"country"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeeByCountry(@RequestParam(value = "country") String country){
        return service.findEmployeeByCountry(country);
    }

    //Получение совершеннолетних юзеров
    @Override
    @GetMapping(value ="users", params = {"isAdult"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAdultUsers(@RequestParam(value = "isAdult") Boolean isAdult) {
        return service.findAdultUser(isAdult);
    }

    //Получение юзеров пользователей гугл почты
    @Override
    @GetMapping(value ="users", params = {"email"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getGmailUser(@RequestParam(value = "email") String email) {
        return service.findEmployeeByEmail(email);
    }

    //Получение юзеров по полю isDeleted
    @Override
    @GetMapping(value ="users", params = {"isdeleted"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getUserByIsDeletedValue(@RequestParam(value = "isdeleted") Boolean isDeleted) {
        return service.findAllByIsDeleted(isDeleted);
    }
}
