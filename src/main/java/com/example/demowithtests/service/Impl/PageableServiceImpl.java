package com.example.demowithtests.service.Impl;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.PageableRepository;
import com.example.demowithtests.service.PageableService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@org.springframework.stereotype.Service
public class PageableServiceImpl implements PageableService {

    private final PageableRepository pageableRepository;

    @Override
    public Page<Employee> getAllWithPagination(Pageable pageable) {
        log.debug("getAllWithPagination() - start: pageable = {}", pageable);
        Page<Employee> list = pageableRepository.findAll(pageable);
        log.debug("getAllWithPagination() - end: list = {}", list);
        return list;
    }

    @Override
    public Page<Employee> findUsersWithPhoneNumberPageable(int page, int size,
                                                           List<String> sortList, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        return pageableRepository.findUsersWithPhoneNumberPageable(pageable);
    }


    @Override
    public Page<Employee> findAllByNamePagination(String name, int page, int size,
                                                 List<String> sortList, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        return pageableRepository.findByName(name, pageable);
    }

    @Override
    public Page<Employee> findByCountryContaining(String country, int page, int size,
                                                  List<String> sortList, String sortOrder) {
        // create Pageable object using the page, size and sort details
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        // fetch the page object by additionally passing pageable with the filters
        return pageableRepository.findByCountryContaining(country, pageable);
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

    @Override
    public Page<Employee> findEmployeesByGmail(int page, int size,
                                               List<String> sortList, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        return pageableRepository.findEmployeesByGmail(pageable);
    }
}
