package com.example.demowithtests.domain;

import com.example.demowithtests.util.annotations.entity.Name;
import com.example.demowithtests.util.annotations.entity.ToLowerCase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@NamedEntityGraph(
        name = "employee-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "addresses")

        }
        )
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public final class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Name
    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

//    @ToLowerCase
//    @Email(message = "Email should be valid")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    @OrderBy("id desc, country asc")
    private Set<Address> addresses = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Gender gender;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    @JsonIgnore
    private Document document;

    @Builder.Default
    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;
}
