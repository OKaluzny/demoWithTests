package com.example.demowithtests.service.grpc;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.grpc.*;
import com.example.demowithtests.service.EmployeeService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeGrpcServiceImpl extends EmployeeServiceGrpc.EmployeeServiceImplBase {
    private final EmployeeService employeeService;
    private final ProtoConverter protoConverter;

    @Override
    public void createEmployee(CreateEmployeeRequest request, StreamObserver<EmployeeResponse> responseObserver) {
        if (request == null || request.getName().trim().isEmpty()) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Employee name cannot be null or empty")
                    .asRuntimeException());
            return;
        }

        log.info("Creating employee with name: {}", request.getName());
        handleGrpcCall(responseObserver, () -> {
            Employee employee = protoConverter.toEmployee(request);
            Employee createdEmployee = employeeService.create(employee);
            return buildEmployeeResponse(createdEmployee);
        });
    }

    @Override
    public void getEmployee(GetEmployeeRequest request, StreamObserver<EmployeeResponse> responseObserver) {
        if (!validateEmployeeId(request, responseObserver)) {
            return;
        }

        log.info("Getting employee with id: {}", request.getId());
        handleGrpcCall(responseObserver, () -> {
            Employee employee = employeeService.getById(request.getId());
            return buildEmployeeResponse(employee);
        });
    }

    @Override
    public void updateEmployee(UpdateEmployeeRequest request, StreamObserver<EmployeeResponse> responseObserver) {
        if (!validateEmployeeId(request, responseObserver)) {
            return;
        }

        log.info("Updating employee with id: {}", request.getId());
        handleGrpcCall(responseObserver, () -> {
            Employee employee = protoConverter.toEmployee(request);
            Employee updatedEmployee = employeeService.updateById(request.getId(), employee);
            return buildEmployeeResponse(updatedEmployee);
        });
    }

    @Override
    public void deleteEmployee(DeleteEmployeeRequest request, StreamObserver<DeleteEmployeeResponse> responseObserver) {
        if (!validateEmployeeId(request, responseObserver)) {
            return;
        }

        log.info("Deleting employee with id: {}", request.getId());
        handleGrpcCall(responseObserver, () -> {
            employeeService.removeById(request.getId());
            return DeleteEmployeeResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Employee deleted successfully")
                    .build();
        });
    }

    private boolean validateEmployeeId(Object request, StreamObserver<?> responseObserver) {
        if (request == null) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Employee ID must be positive")
                    .asRuntimeException());
            return false;
        }

        Integer id = null;
        if (request instanceof GetEmployeeRequest) {
            id = ((GetEmployeeRequest) request).getId();
        } else if (request instanceof UpdateEmployeeRequest) {
            id = ((UpdateEmployeeRequest) request).getId();
        } else if (request instanceof DeleteEmployeeRequest) {
            id = ((DeleteEmployeeRequest) request).getId();
        }

        if (id == null || id <= 0) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Employee ID must be positive")
                    .asRuntimeException());
            return false;
        }

        return true;
    }

    private <T> void handleGrpcCall(StreamObserver<T> responseObserver, Supplier<T> action) {
        try {
            T response = action.get();
            if (response == null) {
                responseObserver.onError(Status.INTERNAL
                        .withDescription("Response cannot be null")
                        .asRuntimeException());
                return;
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            log.error("Invalid argument in gRPC call", e);
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (jakarta.persistence.EntityNotFoundException e) {
            log.error("Entity not found in gRPC call", e);
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (RuntimeException e) {
            log.error("Runtime error in gRPC call", e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Internal server error")
                    .asRuntimeException());
        } catch (Exception e) {
            log.error("Unexpected error in gRPC call", e);
            responseObserver.onError(Status.UNKNOWN
                    .withDescription("Unknown error occurred")
                    .asRuntimeException());
        }
    }

    private EmployeeResponse buildEmployeeResponse(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        EmployeeProto employeeProto = protoConverter.toEmployeeProto(employee);
        return EmployeeResponse.newBuilder()
                .setEmployee(employeeProto)
                .build();
    }
}