package com.example.demowithtests.domain;

import com.example.demowithtests.util.annotations.entity.Name;
import com.example.demowithtests.util.annotations.entity.ToLowerCase;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "users")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Name
    private String name;
    @ToLowerCase
    private String country;
    private String email;
    private boolean isDeleted;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Set<Address> addresses = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "employee")
    private Set<UsersWorkPlaces> usersWorkPlaces = new HashSet<>();

//    @ManyToMany
//    @JoinTable(
//            name = "users_work_places",
//            joinColumns = @JoinColumn(name = "employee_id"),
//            inverseJoinColumns = @JoinColumn(name = "work_places_id"))
//    private Set<WorkPlace> workPlaces = new HashSet<>();

}
