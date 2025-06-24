package com.example.demowithtests.util.config;

import com.example.demowithtests.service.grpc.EmployeeGrpcService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class GrpcServerConfig {

    private final EmployeeGrpcService employeeGrpcService;

    @Value("${grpc.server.port:9090}")
    private int grpcPort;

    @Bean
    public Server grpcServer() {
        return ServerBuilder.forPort(grpcPort)
                .addService(employeeGrpcService.bindService())
                .build();
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public GrpcServerLifecycle grpcServerLifecycle(Server grpcServer) {
        return new GrpcServerLifecycle(grpcServer);
    }

    public static class GrpcServerLifecycle {
        private final Server grpcServer;

        public GrpcServerLifecycle(Server grpcServer) {
            this.grpcServer = grpcServer;
        }

        public void start() throws IOException {
            grpcServer.start();
            log.info("gRPC Server started, listening on port {}", grpcServer.getPort());

            // Add shutdown hook to stop the server when JVM is shutting down
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                log.info("Shutting down gRPC server...");
                grpcServer.shutdown();
                log.info("gRPC server shut down successfully");
            }));
        }

        public void shutdown() {
            if (grpcServer != null) {
                grpcServer.shutdown();
                log.info("gRPC server shut down");
            }
        }
    }
}
