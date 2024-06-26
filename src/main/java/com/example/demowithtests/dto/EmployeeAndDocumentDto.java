package com.example.demowithtests.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeAndDocumentDto {
    private Integer id;
    private String name;
    private String country;
    private String email;
    private String documentNumber;
    private DocDto document;
}
