package com.example.demowithtests.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DocDto {
    private Integer id;
    private String number;
    private String uuid;
    private LocalDateTime expireDate;
    private Boolean isHandled;
}
