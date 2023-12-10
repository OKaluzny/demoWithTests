package com.example.demowithtests.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "date_and_time")
    private LocalDateTime dateAndTime;

    @ManyToOne(targetEntity = Document.class)
    @JoinColumn(name = "document", referencedColumnName = "id")
    private Document document;
}
