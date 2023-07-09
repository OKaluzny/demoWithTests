package com.example.demowithtests.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "photo")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PassportPhoto {

    @Id
    @Column(name = "passport_id")
    private Integer passportId;
    private String photoLink;

    @OneToOne
    @MapsId
    @JoinColumn(name = "passport_id")
    @JsonIgnore
    private Passport passport;

}
