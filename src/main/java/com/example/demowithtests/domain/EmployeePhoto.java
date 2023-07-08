package com.example.demowithtests.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "photo")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmployeePhoto {

    @Id
    @Column(name = "passport_id")
    private Integer passportId;
    private String photo;

    @OneToOne
    @MapsId
    @JoinColumn(name = "passport_id")
    @JsonIgnore
    private EmployeePassport passport;

}
