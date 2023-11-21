package com.example.demowithtests.dto;


public record CountDto(

        Long count
) {
    public CountDto(
            Long count
    ) {
        this.count = count;
    }

}
