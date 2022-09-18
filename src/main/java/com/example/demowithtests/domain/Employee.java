package com.example.demowithtests.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String country;
    private String email;

    private Boolean isUpdated;
    private Integer phoneNumber;
    private Boolean isDeleted = Boolean.FALSE;
}
