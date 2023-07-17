package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "work_places")
public class WorkPlace implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(nullable = false)
    private Boolean airCondition = Boolean.TRUE;
    @Column(nullable = false)
    private Boolean coffeeMachine = Boolean.TRUE;

    @OneToMany(mappedBy = "workPlace")
    private Set<UsersWorkPlaces> usersWorkPlaces = new HashSet<>();

//    @ManyToMany(mappedBy = "workPlaces")
//    @JsonIgnore
//    private Set<Employee> employee = new HashSet<>();

}