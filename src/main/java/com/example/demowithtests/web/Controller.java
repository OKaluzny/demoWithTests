package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeCreateDto;
import com.example.demowithtests.dto.EmployeeReadTechDto;
import com.example.demowithtests.dto.EmployeeToReadDto;
import com.example.demowithtests.dto.EmployeeUpdateDto;
import com.example.demowithtests.service.Service;
import com.example.demowithtests.util.config.mapstruct.EmployeeMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controller {

    private final Service service;
    private final EmployeeMapper mapper;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody Employee employee) {
        return service.create(employee);
    }

    @PostMapping("/users/create_dto")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeCreateDto saveEmployeeDto(@RequestBody Employee employee) {

        return mapper.createDto(service.create(employee));
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllUsers() {
        return service.getAll();
    }

    @GetMapping("/users/dtos")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeToReadDto> getAllEmployeeDto() {
        return mapper.toDtoList(service.getAll());
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployeeById(@PathVariable Integer id) {

        Employee employee = service.getById(id);
        return employee;
    }

    @GetMapping(value = "/users/country_dto", params = {"country"})
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeToReadDto> getEmployeeByCountry(@RequestParam(value = "country") String country) {

        return mapper.readByCountry(service.findEmployeesByCountry(country));
    }

    @GetMapping("/users/{id}/dto")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeToReadDto getEmployeeByIdDto(@PathVariable Integer id) {
        return mapper.employeeToReadDto(service.getById(id));
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee refreshEmployee(@PathVariable("id") Integer id, @RequestBody Employee employee) {
        return service.updateById(id, employee);
    }

    @PatchMapping("/users/{id}/phone_number_dto")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeUpdateDto refreshDtoPhoneNumber(@PathVariable("id") Integer id,
                                                   @RequestParam(value = "phoneNumber") Integer phoneNumber) {
        return mapper.updatePhoneNumberDto(service.updatePhoneById(id, phoneNumber));
    }

    @GetMapping("/users/tech")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadTechDto> getAllUsersTech() {
        return mapper.getTechDto(service.getAll());
    }

    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Integer id) {
        service.removeById(id);
    }

    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        service.removeAll();
    }

    @GetMapping(value = "/users", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllByName(@RequestParam(value = "name") String name) {
        return service.findAllByName(name);
    }

    @GetMapping(value = "/users/phone_number")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getUsersWithPhoneNumber() {
        return service.findUsersWithPhoneNumber();
    }

    @GetMapping("/users/no_email")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> generateEmail() {
        return service.findRecordsWhereEmailNull();
    }

    @GetMapping(value = "/users", params = {"country"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getUsersByCountry(@RequestParam(value = "country") String country) {
        return service.findEmployeesByCountry(country);
    }
}
