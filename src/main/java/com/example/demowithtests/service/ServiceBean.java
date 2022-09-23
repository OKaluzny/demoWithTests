package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.Repository;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityNotFoundException;
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
}
