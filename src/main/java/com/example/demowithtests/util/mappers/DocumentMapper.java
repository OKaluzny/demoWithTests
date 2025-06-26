package com.example.demowithtests.util.mappers;

import com.example.demowithtests.domain.Document;
import com.example.demowithtests.dto.DocumentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {java.util.UUID.class, java.time.LocalDateTime.class})
public interface DocumentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "expireDate", expression = "java(java.time.LocalDateTime.now().plusYears(1))")
    @Mapping(target = "isHandled", constant = "false")
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "history", ignore = true)
    Document toDocument(DocumentDto documentDto);
}
