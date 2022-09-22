package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.Repository;
import com.example.demowithtests.util.ResourceNotFoundException;
import com.example.demowithtests.util.ResourceWasDeletedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityNotFoundException;
import java.util.*;

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
        return repository.findAll();
    }

    @Override
    public Employee getById(Integer id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
//                .orElseThrow(ResourceNotFoundException::new);
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
                    return repository.save(entity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
    }

    @Override
    public void removeById(Integer id) {
        //repository.deleteById(id);
        Employee employee = repository.findById(id)
                // .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
                .orElseThrow(ResourceWasDeletedException::new);
        //employee.setIsDeleted(true);
        repository.delete(employee);
        //repository.save(employee);
    }

    @Override
    public void removeAll() {
        repository.deleteAll();

    }

    @Override
    public void isDeleted(Integer id) {
        Employee employee = repository.findById(id)
                .orElseThrow(ResourceWasDeletedException::new);
        employee.setDeleted(true);
        repository.save(employee);
    }

    // get list users where deleted = false
    @Override
    public List<Employee> getAllUsers() {
        List<Employee> list = new ArrayList<>();
        List<Employee> employee = repository.findAll();

        for (Employee value : employee) {
            if (!value.isDeleted()) {
                list.add(value);
            }
        }

        return list;
    }

    // №1 get employee by name
    @Override
    public List<Employee> getName(String name) {
        return repository.getName(name);
    }

    // №2  get access
    @Override
    public List<Employee> isAccess(Integer id) {
        repository.isAccess(id);
        return repository.getIsAccess();
    }

    @Override
    public List<Employee> getListCountry(String country) {
        return repository.getListCountry(country);
    }

    @Override
    public void updateEmail(Integer id, String email) {
        repository.updateEmail(id, email);

    }


    //    // №3 get hour
//    @Override
//    public void updateHour(Integer id, Double hour) {
//        repository.updateHour(id, hour);
//    }
//
    // №4 get salary
//    @Override
//    public void getSalary(Integer id) {
//        Employee employee = repository.findById(id)
//                .orElseThrow(ResourceWasDeletedException::new);
//
//        double rate = 50;
//
//        // salary = hour * rate
//        employee.setSalary(employee.getHour() * rate);
//        repository.getSalary(id, employee.getSalary());
//    }
//
//    // get list name and salary
//    @Override
//    public List<Object> salaryInfo() {
//        return repository.listSalary();
//    }
}
