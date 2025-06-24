package com.example.demowithtests.service.grpc;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.service.EmployeeService;
import io.grpc.MethodDescriptor;
import io.grpc.ServerServiceDefinition;
import io.grpc.stub.ServerCalls;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeGrpcService {

    private final EmployeeService employeeService;

    public ServerServiceDefinition bindService() {
        return ServerServiceDefinition.builder("EmployeeService")
                .addMethod(
                        MethodDescriptor.newBuilder()
                                .setType(MethodDescriptor.MethodType.UNARY)
                                .setFullMethodName("EmployeeService/CreateEmployee")
                                .setRequestMarshaller(new ProtoMarshaller<>())
                                .setResponseMarshaller(new ProtoMarshaller<>())
                                .build(),
                        ServerCalls.asyncUnaryCall((request, responseObserver) -> createEmployee(request, responseObserver))
                )
                .addMethod(
                        MethodDescriptor.newBuilder()
                                .setType(MethodDescriptor.MethodType.UNARY)
                                .setFullMethodName("EmployeeService/GetEmployee")
                                .setRequestMarshaller(new ProtoMarshaller<>())
                                .setResponseMarshaller(new ProtoMarshaller<>())
                                .build(),
                        ServerCalls.asyncUnaryCall((request, responseObserver) -> getEmployee(request, responseObserver))
                )
                .addMethod(
                        MethodDescriptor.newBuilder()
                                .setType(MethodDescriptor.MethodType.UNARY)
                                .setFullMethodName("EmployeeService/UpdateEmployee")
                                .setRequestMarshaller(new ProtoMarshaller<>())
                                .setResponseMarshaller(new ProtoMarshaller<>())
                                .build(),
                        ServerCalls.asyncUnaryCall((request, responseObserver) -> updateEmployee(request, responseObserver))
                )
                .addMethod(
                        MethodDescriptor.newBuilder()
                                .setType(MethodDescriptor.MethodType.UNARY)
                                .setFullMethodName("EmployeeService/DeleteEmployee")
                                .setRequestMarshaller(new ProtoMarshaller<>())
                                .setResponseMarshaller(new ProtoMarshaller<>())
                                .build(),
                        ServerCalls.asyncUnaryCall((request, responseObserver) -> deleteEmployee(request, responseObserver))
                )
                .build();
    }

    private void createEmployee(Object request, StreamObserver<Object> responseObserver) {
        try {
            // In a real implementation, we would deserialize the request to get employee data
            // For simplicity, we'll create a dummy employee
            Employee employee = Employee.builder()
                    .name("John Doe")
                    .email("john.doe@example.com")
                    .country("USA")
                    .build();

            Employee createdEmployee = employeeService.create(employee);

            // In a real implementation, we would serialize the response
            responseObserver.onNext(createdEmployee);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error creating employee", e);
            responseObserver.onError(e);
        }
    }

    private void getEmployee(Object request, StreamObserver<Object> responseObserver) {
        try {
            // In a real implementation, we would deserialize the request to get the employee ID
            // For simplicity, we'll use a hardcoded ID
            Integer id = 1;

            Employee employee = employeeService.getById(id);

            // In a real implementation, we would serialize the response
            responseObserver.onNext(employee);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error getting employee", e);
            responseObserver.onError(e);
        }
    }

    private void updateEmployee(Object request, StreamObserver<Object> responseObserver) {
        try {
            // In a real implementation, we would deserialize the request to get employee data
            // For simplicity, we'll use a hardcoded ID and employee data
            Integer id = 1;
            Employee employee = Employee.builder()
                    .id(id)
                    .name("John Updated")
                    .email("john.updated@example.com")
                    .country("Canada")
                    .build();

            Employee updatedEmployee = employeeService.updateById(id, employee);

            // In a real implementation, we would serialize the response
            responseObserver.onNext(updatedEmployee);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error updating employee", e);
            responseObserver.onError(e);
        }
    }

    private void deleteEmployee(Object request, StreamObserver<Object> responseObserver) {
        try {
            // In a real implementation, we would deserialize the request to get the employee ID
            // For simplicity, we'll use a hardcoded ID
            Integer id = 1;

            employeeService.removeById(id);

            // In a real implementation, we would serialize the response
            responseObserver.onNext("Employee deleted successfully");
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error deleting employee", e);
            responseObserver.onError(e);
        }
    }

    // A simple marshaller for demonstration purposes
    private static class ProtoMarshaller<T> implements MethodDescriptor.Marshaller<T> {
        @Override
        public T parse(java.io.InputStream stream) {
            // In a real implementation, we would deserialize the input stream to the appropriate type
            return null;
        }

        @Override
        public java.io.InputStream stream(T value) {
            // In a real implementation, we would serialize the value to an input stream
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(value);
                oos.close();
                return new java.io.ByteArrayInputStream(baos.toByteArray());
            } catch (IOException e) {
                throw new RuntimeException("Failed to serialize object", e);
            }
        }
    }
}
