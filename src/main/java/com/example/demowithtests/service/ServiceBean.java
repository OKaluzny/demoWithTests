package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.Repository;
import com.example.demowithtests.util.ResourceNotFoundException;
import com.example.demowithtests.util.ResourceWasDeletedException;
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
        return repository.save(employee);
    }

    @Override
    public List<Employee> getAll() {


        return repository.findByDeleted(false);
    }

    @Override
    public Employee getById(Integer id) {
        Employee employee = repository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
                //.orElseThrow(ResourceNotFoundException::new);
        if (employee.isDeleted()) {
            throw new EntityNotFoundException("Employee was deleted with id = " + id);
       }
        return employee;
    }

    @Override
    public Employee updateById(Integer id, Employee employee) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setName(employee.getName());
                    entity.setEmail(employee.getEmail());
                    entity.setCountry(employee.getCountry());
                    entity.setDeleted(employee.isDeleted());
                    return repository.save(entity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
    }

    @Override
    public void removeById(Integer id) {
        //repository.deleteById(id);
        Employee employee = repository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
               // .orElseThrow(ResourceWasDeletedException::new);
        employee.setDeleted(true);
        //repository.delete(employee);
        repository.save(employee);
    }

    @Override
    public void removeAll() {
        List<Employee> employees = repository.findAll();
        for (Employee employee : employees) {
            employee.setDeleted(true);
        }

        repository.saveAll(employees);

    }
}
