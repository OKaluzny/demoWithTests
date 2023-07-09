package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "passport")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private Long number;
    @Column(unique = true)
    private String series;
    private LocalDateTime expireDate;
    private final String uuid = UUID.randomUUID().toString();
    private boolean isHanded = false;

    @OneToOne(mappedBy = "passport")
    private Employee employee;

    @OneToOne(mappedBy = "passport", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PassportPhoto photo;

}
