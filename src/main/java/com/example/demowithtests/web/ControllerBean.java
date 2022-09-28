package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.service.Service;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerBean {

    private final Service service;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Employee saveEmployee(@RequestBody Employee employee) {
        return service.create(employee);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllUsers() {
        return service.getAll();
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployeeById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee refreshEmployee(@PathVariable("id") Integer id, @RequestBody Employee employee) {
        return service.updateById(id, employee);
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

    @GetMapping("/users/p")
    @ResponseStatus(HttpStatus.OK)
    public Page<Employee> getPage(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        return service.getAllWithPagination(paging);
    }

    @GetMapping("/users/p/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Page<Employee> findByName(@RequestParam(required = false) String name,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "3") int size,
                                     @RequestParam(defaultValue = "") List<String> sortList,
                                     @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        return service.findByCountryContaining(name, page, size, sortList, sortOrder.toString());
    }

    @GetMapping("/users/p/phone_numbers")
    @ResponseStatus(HttpStatus.OK)
    public Page<Employee> findWithPhoneNumber(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "3") int size,
                                              @RequestParam(defaultValue = "") List<String> sortList,
                                              @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        return service.findUsersWithPhoneNumberPageable(page, size, sortList, sortOrder.toString());
    }

    @GetMapping("/users/p/{country}")
    @ResponseStatus(HttpStatus.OK)
    public Page<Employee> findByCountry(@RequestParam(required = false) String country,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "3") int size,
                                        @RequestParam(defaultValue = "") List<String> sortList,
                                        @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        //Pageable paging = PageRequest.of(page, size);
        //Pageable paging = PageRequest.of(page, size, Sort.by("name").ascending());
        return service.findByCountryContaining(country, page, size, sortList, sortOrder.toString());
    }
}
