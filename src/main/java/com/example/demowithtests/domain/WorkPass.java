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
public class WorkPass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid = UUID.randomUUID().toString();
    private String series;
    private Long number;
    private LocalDateTime expireDate;
    private Boolean isHanded = Boolean.FALSE;

    @OneToOne(mappedBy = "workPass")
    private Employee employee;

}
