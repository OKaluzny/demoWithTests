package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.Repository;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@org.springframework.stereotype.Service
public class ServiceBean implements Service {

    private final Repository repository;

    @Override
    public Employee create(Employee employee) {
        employee.setIsUpdated(false);
        return repository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }

    @Override
    public Employee getById(Integer id) {
        Employee employee = repository.findById(id)
                // .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
                .orElseThrow(ResourceNotFoundException::new);
         /*if (employee.getIsDeleted()) {
            throw new EntityNotFoundException("Employee was deleted with id = " + id);
        }*/
        return employee;
    }

    @Override
    public Employee updateById(Integer id, Employee employee) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setName(employee.getName());
                    entity.setEmail(employee.getEmail());
                    entity.setCountry(employee.getCountry());
                    entity.setPhoneNumber(employee.getPhoneNumber());
                    entity.setIsUpdated(true);
                    return repository.save(entity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
    }

    @Override
    public void removeById(Integer id) {
        //repository.deleteById(id);
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
        //.orElseThrow(ResourceWasDeletedException::new);
        employee.setIsDeleted(true);
        //repository.delete(employee);
        repository.save(employee);
    }

    @Override
    public void removeAll() {
        repository.deleteAll();

    }

    @Override
    public List<Employee> findAllByName(String name) {
        return repository.findAllByName(name);
    }

    @Override
    public List<Employee> findUsersWithPhoneNumber() {
        return repository.findUsersWithPhoneNumber();
    }

    @Override
    public List<Employee> findRecordsWhereEmailNull() {
        List<Employee> emailIsNull = repository.findRecordsWhereEmailNull();
        for (Employee element : emailIsNull) {
            element.setEmail(element.getName().substring(0, 1).toLowerCase() + 31 * element.getId() + "@itorg.com");
            repository.save(element);
        }
        return emailIsNull;
    }

    @Override
    public List<Employee> findEmployeesByCountry(String country) {
        return repository.findEmployeesByCountry(country);
    }

    @Override
    public Employee updatePhoneById(Integer id, Integer phoneNumber) {
        Employee employee = repository.findById(id).orElseThrow();
        employee.setPhoneNumber(phoneNumber);
        repository.save(employee);
        return employee;
    }

    @Override
    public Page<Employee> getAllWithPagination(Pageable pageable) {
        log.debug("getAllWithPagination() - start: pageable = {}", pageable);
        Page<Employee> list = repository.findAll(pageable);
        log.debug("getAllWithPagination() - end: list = {}", list);
        return list;
    }

    @Override
    public Page<Employee> findUsersWithPhoneNumberPageable(int page, int size,
                                                           List<String> sortList, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        return repository.findUsersWithPhoneNumberPageable(pageable);
    }


    @Override
    public Page<Employee> getAllByNamePagination(String name, int page, int size,
                                                 List<String> sortList, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        return repository.findByName(name, pageable);
    }

    @Override
    public Page<Employee> findByCountryContaining(String country, int page, int size,
                                                  List<String> sortList, String sortOrder) {
        // create Pageable object using the page, size and sort details
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        // fetch the page object by additionally passing pageable with the filters
        return repository.findByCountryContaining(country, pageable);
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
}
