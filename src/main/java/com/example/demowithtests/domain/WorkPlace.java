package com.example.demowithtests.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "work_places")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Boolean airCondition = Boolean.TRUE;
    private Boolean coffeeMachine = Boolean.TRUE;

    @ManyToMany(mappedBy = "workPlaces")
    @JsonIgnore
    private Set<Employee> employee = new HashSet<>();
}