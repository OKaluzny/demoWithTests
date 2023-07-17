package com.example.demowithtests.dto;

import lombok.Builder;

@Builder
public record WorkPlaceDto(Integer id,
                           String name,
                           Boolean airCondition,
                           Boolean coffeeMachine) {
    public WorkPlaceDto {
        airCondition = airCondition == null ? Boolean.TRUE : airCondition;
        coffeeMachine = coffeeMachine == null ? Boolean.TRUE : coffeeMachine;
    }
}