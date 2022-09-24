package com.example.demowithtests.web.controllers;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.updateDto.EmployeeUpdateDto;
import com.example.demowithtests.service.Service;
import com.example.demowithtests.util.config.EmployeeConverter;
import com.example.demowithtests.web.interfaces.put.PutRefreshRequest;
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

public class PutController implements PutRefreshRequest {
    private final Service service;

    private final EmployeeConverter converter;

    //Обновление юзера
    @Override
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeUpdateDto refreshEmployee(@PathVariable("id") Integer id, @RequestBody Employee employee) {
        var empl = service.updateById(id, employee);
        return converter.toUpdateDto(empl);
    }
}
