package com.example.demowithtests.util.mapper;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.passport.PassportDto;
import com.example.demowithtests.dto.passport.PassportReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Artem Kovalov on 14.06.2023
 */
@Mapper(componentModel = "spring")
public interface PassportMapper {
    PassportMapper INSTANCE = Mappers.getMapper(PassportMapper.class);

    PassportDto toDto(Passport passport);

    PassportReadDto toReadDto(Passport passport);

    Passport toEntity(PassportDto passportDto);

    List<PassportReadDto> listToReadDto(List<Passport> entityList);

}
