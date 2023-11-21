package com.example.demowithtests.dto;

public record FranceGreetingDto (
        String message,

        int numberOfEmployees
){
    public  FranceGreetingDto(
            String message,
            int numberOfEmployees
    ){
        this.numberOfEmployees = numberOfEmployees;
        this.message = message;
    }
}

