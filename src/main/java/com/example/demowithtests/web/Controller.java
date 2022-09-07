package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.service.Service;
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

    //Операция сохранения юзера в базу данных
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody Employee employee) {
        employee.setIsAdult(employee.getAge() >= 18);

        return service.create(employee);
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
    public Employee getEmployeeById(@PathVariable Integer id) {

        Employee employee = service.getById(id);
        return employee;
    }

    //Обновление юзера
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee refreshEmployee(@PathVariable("id") Integer id, @RequestBody Employee employee) {

        return service.updateById(id, employee);
    }

    @PatchMapping(value ="users", params = {"id"})
    @ResponseStatus(HttpStatus.OK)
    public void getAdultUsers(@RequestParam(value = "id") Integer id) {
        service.hideUser(id);
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

    @GetMapping(value ="users", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getListByName(@RequestParam(value = "name") String name){
        return service.findUserByName(name);
    }

    @GetMapping(value ="users", params = {"country"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployeeByCountry(@RequestParam(value = "country") String country){
        return service.findEmployeeByCountry(country);
    }

    @GetMapping(value ="users", params = {"isAdult"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAdultUsers(@RequestParam(value = "isAdult") Boolean isAdult) {
        return service.findAdultUser(isAdult);
    }


    @GetMapping(value ="users", params = {"email"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getGmailUser(@RequestParam(value = "email") String email) {
        return service.findEmployeeByEmail(email);
    }
}
