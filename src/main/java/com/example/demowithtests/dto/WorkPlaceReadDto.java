package com.example.demowithtests.dto;

import com.example.demowithtests.domain.UsersWorkPlaces;

import java.util.Set;

public record WorkPlaceReadDto(Integer id,
                               String name,
                               Boolean airCondition,
                               Boolean coffeeMachine,
                               Set<UsersWorkPlaces> usersWorkPlaces
) {
}