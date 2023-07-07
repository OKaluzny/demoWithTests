package com.example.demowithtests.service;

import com.example.demowithtests.domain.WorkPlace;
import com.example.demowithtests.repository.WorkPlaceRepository;
import org.springframework.stereotype.Service;

@Service
public class WorkPlaceServiceBean implements WorkPlaceService {

  private final WorkPlaceRepository workPlaceRepository;

  public WorkPlaceServiceBean(WorkPlaceRepository workPlaceRepository) {
    this.workPlaceRepository = workPlaceRepository;
  }


  @Override
  public WorkPlace create(WorkPlace workPlace) {
    return workPlaceRepository.save(workPlace);
  }

  @Override
  public WorkPlace getById(Integer id) {
    return workPlaceRepository.findById(id).orElseThrow();
  }
}