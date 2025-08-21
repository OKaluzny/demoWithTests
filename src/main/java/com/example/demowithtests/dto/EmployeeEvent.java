package com.example.demowithtests.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEvent {

    @JsonProperty("eventId")
    private String eventId;

    @JsonProperty("eventType")
    private EventType eventType;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("employeeId")
    private Integer employeeId;

    @JsonProperty("employeeName")
    private String employeeName;

    @JsonProperty("employeeEmail")
    private String employeeEmail;

    @JsonProperty("employeeCountry")
    private String employeeCountry;

    @JsonProperty("previousData")
    private EmployeeData previousData;

    @JsonProperty("currentData")
    private EmployeeData currentData;

    public enum EventType {
        CREATED,
        UPDATED,
        DELETED
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmployeeData {
        @JsonProperty("name")
        private String name;

        @JsonProperty("email")
        private String email;

        @JsonProperty("country")
        private String country;
    }
}