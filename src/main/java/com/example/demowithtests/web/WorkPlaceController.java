package com.example.demowithtests.web;

import com.example.demowithtests.domain.WorkPlace;
import com.example.demowithtests.dto.WorkPlaceDto;
import com.example.demowithtests.dto.WorkPlaceReadDto;
import com.example.demowithtests.service.WorkPlaceService;
import com.example.demowithtests.util.mapper.WorkPlaceMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "WorkPlace", description = "WorkPlace API")
public class WorkPlaceController {
  private final WorkPlaceService workPlaceService;
  private final WorkPlaceMapper mapper;

  @PostMapping("/workplaces")
  @ResponseStatus(HttpStatus.CREATED)
  public WorkPlaceDto create(@RequestBody WorkPlaceDto workPlaceDto) {
    WorkPlace workPlace = mapper.fromWorkPlaceDto(workPlaceDto);
    return mapper.toWorkPlaceDto(workPlaceService.create(workPlace));
  }

  @GetMapping("/workplaces/{id}")
  @ResponseStatus(HttpStatus.OK)
  public WorkPlaceReadDto getById(@PathVariable Integer id) {
    return mapper.toWorkPlaceReadDto(workPlaceService.getById(id));
  }
}