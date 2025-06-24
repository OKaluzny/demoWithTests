package com.example.demowithtests.service.grpc;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.grpc.CreateEmployeeRequest;
import com.example.demowithtests.grpc.EmployeeProto;
import com.example.demowithtests.grpc.UpdateEmployeeRequest;

/**
 * Interface for converting between domain entities and protobuf objects
 */
public interface ProtoConverter {
    
    /**
     * Converts a CreateEmployeeRequest protobuf object to an Employee domain entity
     * 
     * @param request the CreateEmployeeRequest protobuf object
     * @return the Employee domain entity
     */
    Employee toEmployee(CreateEmployeeRequest request);
    
    /**
     * Converts an UpdateEmployeeRequest protobuf object to an Employee domain entity
     * 
     * @param request the UpdateEmployeeRequest protobuf object
     * @return the Employee domain entity
     */
    Employee toEmployee(UpdateEmployeeRequest request);
    
    /**
     * Converts an Employee domain entity to an EmployeeProto protobuf object
     * 
     * @param employee the Employee domain entity
     * @return the EmployeeProto protobuf object
     */
    EmployeeProto toEmployeeProto(Employee employee);
}