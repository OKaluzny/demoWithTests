package com.example.demowithtests.web.interfaces.patch;

import com.example.demowithtests.dto.updateDto.EmployeeUpdateNameDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface PatchUpdateNameRequest {
    @Operation(summary = "This is endpoint allows to update name by id.", description = "Create request to update to update name by id.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.")})
    EmployeeUpdateNameDto updateNameById (@PathVariable("id") Integer id,@RequestParam(value = "name") String name);

}
