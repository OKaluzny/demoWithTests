package com.example.demowithtests.web.controllers;

import com.example.demowithtests.service.Service;
import com.example.demowithtests.web.interfaces.delete.DeleteAllRequest;
import com.example.demowithtests.web.interfaces.delete.DeleteByIdRequest;
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
public class DeleteController implements DeleteByIdRequest, DeleteAllRequest {

    private final Service service;

    @Override
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    // A method that removes an employee by id.
    public void removeEmployeeById(@PathVariable Integer id) {
        service.removeById(id);
    }

    @Override
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    // Removing all users.
    public void removeAllUsers() {
        service.removeAll();
    }

}
