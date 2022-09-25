package com.example.demowithtests.web.interfaces.patch;

import com.example.demowithtests.dto.updateDto.EmployeeUpdateIsDeletedDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

public interface PatchHideRequest {

    @Operation(summary = "This is endpoint allows to update value of field isDeleted.", description = "Create request to update value of field isDeleted.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})
    EmployeeUpdateIsDeletedDto hideAdminEmployee(@PathVariable("id") Integer id);
}
