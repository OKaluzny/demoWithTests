package com.example.demowithtests.util.mapper;

import com.example.demowithtests.domain.WorkPlace;
import com.example.demowithtests.dto.WorkPlaceDto;
import com.example.demowithtests.dto.WorkPlaceReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WorkPlaceMapper {
  WorkPlaceMapper INSTANCE = Mappers.getMapper(WorkPlaceMapper.class);

  WorkPlace fromWorkPlaceDto(WorkPlaceDto workPlaceDto);
  WorkPlaceDto toWorkPlaceDto(WorkPlace workPlace);
  WorkPlace fromWorkPlaceReadDto(WorkPlaceReadDto workPlaceReadDto);
  WorkPlaceReadDto toWorkPlaceReadDto(WorkPlace workPlace);
}