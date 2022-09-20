package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.service.Service;
import com.example.demowithtests.util.config.EmployeeConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Employee", description = "Employee API")
public class Controller {

    private final Service service;
    private final EmployeeConverter converter;

    //Операция сохранения юзера в базу данных
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "This is endpoint to add a new employee.", description = "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto requestForSave) {

        var employee = converter.getMapperFacade().map(requestForSave, Employee.class);
        var dto = converter.toDto(service.create(employee));

        return dto;
    }

    //Получение списка юзеров
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllUsers() {
        return service.getAll();
    }


    //Получения юзера по id
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        log.debug("getEmployeeById() Controller - start: id = {}", id);
        var employee = service.getById(id);
        log.debug("getById() Controller - to dto start: id = {}", id);
        var dto = converter.toReadDto(employee);
        log.debug("getEmployeeById() Controller - end: name = {}", dto.name);
        return dto;
    }

    //Обновление юзера
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee refreshEmployee(@PathVariable("id") Integer id, @RequestBody Employee employee) {

        return service.updateById(id, employee);
    }

    //Скрыть юзера
    @PatchMapping ("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void hideEmployee(@PathVariable("id") Integer id) {
        service.hideEmployee(id);
    }

    //Удаление по id
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Integer id) {
        service.removeById(id);
    }

    //Удаление всех юзеров
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        service.removeAll();
    }

    //Получение юзеров по имени
    @GetMapping(value ="users", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getListByName(@RequestParam(value = "name") String name){
        return service.findUserByName(name);
    }

    //Получение юзеров по стране
    @GetMapping(value ="users", params = {"country"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeeByCountry(@RequestParam(value = "country") String country){
        return service.findEmployeeByCountry(country);
    }

    //Получение совершеннолетних юзеров
    @GetMapping(value ="users", params = {"isAdult"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAdultUsers(@RequestParam(value = "isAdult") Boolean isAdult) {
        return service.findAdultUser(isAdult);
    }

    //Получение юзеров пользователей гугл почты
    @GetMapping(value ="users", params = {"email"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getGmailUser(@RequestParam(value = "email") String email) {
        return service.findEmployeeByEmail(email);
    }

    //Получение юзеров по полю isDeleted
    @GetMapping(value ="users", params = {"isdeleted"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getUserByIsDeletedValue(@RequestParam(value = "isdeleted") Boolean isDeleted) {
        return service.findAllByIsDeleted(isDeleted);
    }

}
