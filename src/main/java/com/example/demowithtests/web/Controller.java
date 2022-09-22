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
        return service.create(employee);
    }

    //Получение списка юзеров
//    @GetMapping("/users")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Employee> getAllUsers() {
//        return service.getAll();
//    }

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

    //Удаление по id
//    @PatchMapping("/users/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void removeEmployeeById(@PathVariable Integer id) {
//        service.removeById(id);
//    }

    //Удаление всех юзеров
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        service.removeAll();
    }

    // get all users is deleted = false
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void isDeletedEmployee(@PathVariable Integer id) {
        service.isDeleted(id);
    }

    // get all movie is deleted = false
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAll() {
        return service.getAllUsers();
    }

    // get list by name
    @GetMapping(value = "/users", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findByName(@RequestParam(value = "name") String name) {
        return service.getName(name);
    }

    // make column access true and get list where access = true
    @PatchMapping("/usersAccess/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> isAccessUsers(@PathVariable Integer id) {
        return service.isAccess(id);
    }



    // get list by country
    @GetMapping(value = "/users", params = {"country"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findByCountry(@RequestParam(value = "country") String country) {
        return service.getListCountry(country);
    }

    @PatchMapping (value = "/users/{id}",params = {"email"})
    @ResponseStatus(HttpStatus.OK)
//    public Employee refreshEmail(@PathVariable("id") Integer id, @RequestBody String email) {
    public Employee refreshEmail(@PathVariable("id") Integer id, @RequestParam(value = "email") String email) {

        service.updateEmail(id, email);
        return service.getById(id);
    }


//    // add hour work
//    @PutMapping("/usersHour/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public void updateHour(@PathVariable("id") Integer id, @RequestBody Double hour) {
//        service.updateHour(id, hour);
//    }
//
//    // add salary
//    @PatchMapping("/usersInfo/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public void getInfo(@PathVariable("id") Integer id) {
//        service.getSalary(id);
//    }
//
//    // get list name, salary
//    @GetMapping("/usersInfo")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Object> infoSalary() {
//        return service.salaryInfo();
//    }
}


