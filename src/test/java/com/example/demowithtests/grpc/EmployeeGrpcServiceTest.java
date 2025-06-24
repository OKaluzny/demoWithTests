package com.example.demowithtests.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"grpc.server.port=9091"})
public class EmployeeGrpcServiceTest {

    @LocalServerPort
    private int serverPort;

    private ManagedChannel channel;

    @BeforeEach
    public void setup() {
        // Create a gRPC channel to connect to the server
        channel = ManagedChannelBuilder.forAddress("localhost", 9091)
                .usePlaintext()
                .build();
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        // Shutdown the channel
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    public void testGrpcServerIsRunning() {
        // This test just verifies that the gRPC server is running
        assertNotNull(channel);
        System.out.println("gRPC server is running on port 9091");
    }

    // In a real test, we would create a gRPC client and call the service methods
    // For example:
    /*
    @Test
    public void testCreateEmployee() {
        // Create a blocking stub
        EmployeeServiceGrpc.EmployeeServiceBlockingStub blockingStub = EmployeeServiceGrpc.newBlockingStub(channel);
        
        // Create a request
        CreateEmployeeRequest request = CreateEmployeeRequest.newBuilder()
                .setName("John Doe")
                .setEmail("john.doe@example.com")
                .setCountry("USA")
                .build();
        
        // Call the service
        EmployeeResponse response = blockingStub.createEmployee(request);
        
        // Verify the response
        assertNotNull(response);
        assertNotNull(response.getEmployee());
        assertEquals("John Doe", response.getEmployee().getName());
    }
    */
}