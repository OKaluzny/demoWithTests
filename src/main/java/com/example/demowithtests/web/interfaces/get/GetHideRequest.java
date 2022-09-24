package com.example.demowithtests.web.interfaces.get;

import com.example.demowithtests.dto.readDto.EmployeeReadAllByIsDeletedDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GetHideRequest {
    @Operation(summary = "This is endpoint returned a hide employees .", description = "Create request to read a hide employees", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})

    List<EmployeeReadAllByIsDeletedDto> getUserByIsDeletedValue(@RequestParam(value = "isdeleted") Boolean isDeleted);
}
