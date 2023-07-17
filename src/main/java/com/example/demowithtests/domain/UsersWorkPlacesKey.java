package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class UsersWorkPlacesKey implements Serializable {
    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "work_places_id")
    private Integer workPlacesId;
}