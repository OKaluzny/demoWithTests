package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.service.PageableService;
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
public class ControllerBeanPageable {

    private final PageableService pageableService;

    @GetMapping("/users/p")
    @ResponseStatus(HttpStatus.OK)
    public Page<Employee> getPage(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        return pageableService.getAllWithPagination(paging);
    }

    @GetMapping("/users/pn/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Page<Employee> findByName(@PathVariable String name,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "3") int size,
                                     @RequestParam(defaultValue = "") List<String> sortList,
                                     @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        return pageableService.findAllByNamePagination(name, page, size, sortList, sortOrder.toString());
    }

    @GetMapping("/users/p/phone_numbers")
    @ResponseStatus(HttpStatus.OK)
    public Page<Employee> findWithPhoneNumber(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "3") int size,
                                              @RequestParam(defaultValue = "") List<String> sortList,
                                              @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        return pageableService.findUsersWithPhoneNumberPageable(page, size, sortList, sortOrder.toString());
    }

    @GetMapping("/users/pc/{country}")
    @ResponseStatus(HttpStatus.OK)
    public Page<Employee> findByCountry(@PathVariable String country,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "3") int size,
                                        @RequestParam(defaultValue = "") List<String> sortList,
                                        @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        //Pageable paging = PageRequest.of(page, size);
        //Pageable paging = PageRequest.of(page, size, Sort.by("name").ascending());
        return pageableService.findByCountryContaining(country, page, size, sortList, sortOrder.toString());
    }

    @GetMapping("/users/p/gmail")
    @ResponseStatus(HttpStatus.OK)
    public Page<Employee> findEmployeeByGmail(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "3") int size,
                                                       @RequestParam(defaultValue = "") List<String> sortList,
                                                       @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {

        Page<Employee> employees = pageableService.findEmployeesByGmail(page, size, sortList, sortOrder.toString());
        //Page<EmployeeToReadDto> employeeToReadDtos = pageMapper.employeeGmail(employees);
        //employees.stream().map(mapper::employeeToReadDto);
        return employees;
    }

}
