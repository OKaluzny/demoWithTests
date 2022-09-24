package com.example.demowithtests.web.controllers;

import com.example.demowithtests.dto.updateDto.EmployeeUpdateIsDeleted;
import com.example.demowithtests.service.Service;

import com.example.demowithtests.util.config.EmployeeConverter;
import com.example.demowithtests.web.interfaces.patch.PatchHideRequest;
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
public class PatchController implements PatchHideRequest {

    private final Service service;
    private final EmployeeConverter converter;
    //Скрыть юзера
    @Override
    @PatchMapping ("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeUpdateIsDeleted hideAdminEmployee(@PathVariable("id") Integer id) {
        var employee = service.hideEmployee(id);
        var dto = converter.toUpdateIsDeletedDto(employee);
        return dto;
    }

}
