package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeCreateDto;
import com.example.demowithtests.dto.EmployeeReadTechDto;
import com.example.demowithtests.dto.EmployeeToReadDto;
import com.example.demowithtests.dto.EmployeeUpdateDto;
import com.example.demowithtests.service.Service;
import com.example.demowithtests.util.config.mapstruct.EmployeeMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerBeanDto implements ControllerDto {

    private final Service service;
    private final EmployeeMapper mapper;

    @Override
    @PostMapping("/users/create_dto")
    public EmployeeCreateDto saveEmployeeDto(@RequestBody @Valid EmployeeCreateDto employeeCreateDto) {
        Employee employee = mapper.createEmployeeFromDto(employeeCreateDto);
        return mapper.createDto(service.create(employee));
    }

    @Override
    @GetMapping("/users/dtos")
    public List<EmployeeToReadDto> getAllEmployeeDto() {
        return mapper.toDtoList(service.getAll());
    }

    @Override
    @GetMapping(value = "/users/country_dto", params = {"country"})
    public List<EmployeeToReadDto> getEmployeeByCountry(@RequestParam(value = "country") String country) {
        return mapper.readByCountry(service.findEmployeesByCountry(country));
    }

    @Override
    @GetMapping("/users/{id}/dto")
    public EmployeeToReadDto getEmployeeByIdDto(@PathVariable Integer id) {
        return mapper.employeeToReadDto(service.getById(id));
    }

    @Override
    @PutMapping("/users/{id}/update_dto")
    public EmployeeUpdateDto refreshEmployeeDto(@PathVariable("id") Integer id,
                                                @RequestBody @Valid EmployeeUpdateDto employeeUpdateDto) {
        Employee employee = mapper.updateEmployeeFromDto(employeeUpdateDto);
        return mapper.updateEmployeeDto(service.updateById(id, employee));
    }

    @Override
    @PatchMapping("/users/{id}/phone_number_dto")
    public EmployeeUpdateDto refreshDtoPhoneNumber(@PathVariable("id") Integer id,
                                                   @RequestParam(value = "phoneNumber") Integer phoneNumber) {
        return mapper.updatePhoneNumberDto(service.updatePhoneById(id, phoneNumber));
    }

    @Override
    @GetMapping("/users/tech")
    public List<EmployeeReadTechDto> getAllUsersTech() {
        return mapper.getTechDto(service.getAll());
    }
}
