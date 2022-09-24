package com.example.demowithtests.web.interfaces.get;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.readDto.EmployeeReadAllByGmailDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GetGmailUsersRequest {
    @Operation(summary = "This is endpoint returned a employees who use google mail.", description = "Create request to read a employees who use google mail", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})

    List<EmployeeReadAllByGmailDto> getGmailUser(@RequestParam(value = "email") String email);
}
