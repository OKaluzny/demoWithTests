package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.service.EmployeeServiceEM;
import com.example.demowithtests.util.mappers.EmployeeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Employee", description = "Employee API")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeServiceEM employeeServiceEM;
    private final EmployeeMapper employeeMapper;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "This is endpoint to add a new employee.", description = "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto requestForSave) {
        log.debug("saveEmployee() - start: requestForSave = {}", requestForSave.name());
        var employee = employeeMapper.toEmployee(requestForSave);
        var dto = employeeMapper.toEmployeeDto(employeeService.create(employee));
        log.debug("saveEmployee() - stop: dto = {}", dto.name());
        return dto;
    }

    @PostMapping("/users/jpa")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveEmployee(@RequestBody Employee employee) {
        log.debug("saveEmployeeWithJpa() - start: employee = {}", employee);
        employeeServiceEM.createWithJpa(employee);
        log.debug("saveEmployeeWithJpa() - stop: employee = {}", employee.getId());
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllUsers() {
        return employeeService.getAll();
    }

    @GetMapping("/users/pages")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> getPage(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        log.debug("getPage() - start: page= {}, size = {}", page, size);
        var paging = PageRequest.of(page, size);
        var content = employeeService.getAllWithPagination(paging)
                .map(employeeMapper::toEmployeeReadDto);
        log.debug("getPage() - end: content = {}", content);
        return content;
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        log.debug("getEmployeeById() EmployeeController - start: id = {}", id);
        var employee = employeeService.getById(id);
        log.debug("getById() EmployeeController - to dto start: id = {}", id);
        var dto = employeeMapper.toEmployeeReadDto(employee);
        log.debug("getEmployeeById() EmployeeController - end: name = {}", dto.name);
        return dto;
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto refreshEmployee(@PathVariable("id") Integer id, @RequestBody EmployeeDto employee) {
        log.debug("refreshEmployee() EmployeeController - start: id = {}", id);
        Employee entity = employeeMapper.toEmployee(employee);
        EmployeeReadDto dto = employeeMapper.toEmployeeReadDto(employeeService.updateById(id, entity));
        log.debug("refreshEmployee() EmployeeController - end: name = {}", dto.name);
        return dto;
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Integer id) {
        employeeService.removeById(id);
    }

    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        employeeService.removeAll();
    }

    @GetMapping("/users/country")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> findByCountry(@RequestParam(required = false) String country,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "3") int size,
                                               @RequestParam(defaultValue = "") List<String> sortList,
                                               @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        //Pageable paging = PageRequest.of(page, size);
        //Pageable paging = PageRequest.of(page, size, Sort.by("name").ascending());
        return employeeService.findByCountryContaining(country, page, size, sortList, sortOrder.toString()).map(employeeMapper::toEmployeeReadDto);
    }

    @GetMapping("/users/c")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersC() {
        return employeeService.getAllEmployeeCountry();
    }

    @GetMapping("/users/s")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersSort() {
        return employeeService.getSortCountry();
    }

    @GetMapping("/users/emails")
    @ResponseStatus(HttpStatus.OK)
    public Optional<String> getAllUsersSo() {
        return employeeService.findEmails();
    }

    @GetMapping("/users/countryBy")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getByCountry(@RequestParam(required = true) String country) {
        return employeeService.filterByCountry(country);
    }

    @PatchMapping("/users/ukrainians")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> sendEmailsAllUkrainian() {
        return employeeService.sendEmailsAllUkrainian();
    }

    @GetMapping("/users/names")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findByNameContaining(@RequestParam String employeeName) {
        log.debug("findByNameContaining() EmployeeController - start: employeeName = {}", employeeName);
        List<Employee> employees = employeeService.findByNameContaining(employeeName);
        log.debug("findByNameContaining() EmployeeController - end: employees = {}", employees.size());
        return employees;
    }
}
