package com.example.demowithtests.web;

import com.example.demowithtests.dto.EmployeeCreateDto;
import com.example.demowithtests.dto.EmployeeReadTechDto;
import com.example.demowithtests.dto.EmployeeToReadDto;
import com.example.demowithtests.dto.EmployeeUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public interface ControllerDto {

    /**
     * This is endpoint to add a new employee
     *
     * @param employeeCreateDto The object that will be passed to the method.
     * @return EmployeeCreateDto
     */
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "This is endpoint to add a new employee.", description = "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new dto employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Dto employee already exists")})
    EmployeeCreateDto saveEmployeeDto(EmployeeCreateDto employeeCreateDto);

    /**
     * This is endpoint returned an employee by his id
     *
     * @param id The id of the employee to be returned.
     * @return EmployeeToReadDto
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    EmployeeToReadDto getEmployeeByIdDto(Integer id);

    /**
     * This is endpoint to get all employees
     *
     * @return List of EmployeeToReadDto
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint to get all employees.", description = "Create request to read all employees", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Employees was found."),
            @ApiResponse(responseCode = "204", description = "There are no employees in database"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
    })
    List<EmployeeToReadDto> getAllEmployeeDto();

    /**
     * This is endpoint returned an employee by his country
     *
     * @param country The country of the employee.
     * @return List of employees by country.
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint returned a employee by his country.", description = "Create request to read employee by country", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})
    List<EmployeeToReadDto> getEmployeeByCountry(@RequestParam(value = "country") String country);

    /**
     * This is endpoint allows to update phone number
     *
     * @param id The id of the employee to be updated.
     * @param phoneNumber The phone number of the employee.
     * @return EmployeeUpdateDto
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint allows to update phone number.", description = "Create request to update phone number.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. Phone number was updated."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})
    EmployeeUpdateDto refreshDtoPhoneNumber(@PathVariable("id") Integer id,
                                            @RequestParam(value = "phoneNumber") Long phoneNumber);

    /**
     * This is endpoint allows to update all fields of employee
     *
     * @param id The id of the employee to be updated.
     * @param employeeUpdateDto The object that will be used to update the employee.
     * @return EmployeeUpdateDto
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint allows to update all fields of employee.", description = "Create request to update employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. Employee was updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})
    EmployeeUpdateDto refreshEmployeeDto(@PathVariable("id") Integer id,
                                         @RequestBody EmployeeUpdateDto employeeUpdateDto);

    /**
     * This is endpoint allows to view all technical fields
     *
     * @return List of EmployeeReadTechDto objects.
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint allows to view all technical fields.", description = "Create request to view technical fields.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK."),
            @ApiResponse(responseCode = "204", description = "There are no employees in database")})
    List<EmployeeReadTechDto> getAllUsersTech();
}
