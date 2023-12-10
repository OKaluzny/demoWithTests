package com.example.demowithtests.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "documents")
public final class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String number;

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

    private LocalDateTime expireDate;

    @Builder.Default
    private Boolean isHandled = Boolean.FALSE;

    @ToString.Exclude
    @OneToOne(mappedBy = "document")
    private Employee employee;

    @JsonIgnore
    @OneToMany(targetEntity = History.class, mappedBy = "document")
    private List<History> history;

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;*/
}
