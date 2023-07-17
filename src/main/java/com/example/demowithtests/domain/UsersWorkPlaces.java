package com.example.demowithtests.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_work_places")
@Entity
@EqualsAndHashCode(of = {"usersWorkPlacesId"})
public class UsersWorkPlaces implements Serializable {
    @EmbeddedId
    private UsersWorkPlacesKey usersWorkPlacesId;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

    @ManyToOne
    @MapsId("workPlacesId")
    @JoinColumn(name = "work_places_id")
    @JsonIgnore
    private WorkPlace workPlace;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isActive = Boolean.TRUE;

}