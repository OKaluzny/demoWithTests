package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.updateDto.EmployeeUpdateNameDto;
import com.example.demowithtests.repository.JpqlRepository;
import com.example.demowithtests.repository.Repository;
import com.example.demowithtests.repository.SqlRepository;
import com.example.demowithtests.util.exeption.ResourceWasDeletedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@AllArgsConstructor
@Slf4j
@org.springframework.stereotype.Service
public class ServiceBean implements Service {

    private final Repository repository;
    private final SqlRepository sqlRepository;
    private final JpqlRepository jpqlRepository;

    @Override
    public Employee create(Employee employee) {
        employee.setIsAdult(employee.getAge() >= 18);
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
                //.orElseThrow(ResourceNotFoundException::new);
         if (employee.getIsDeleted()) {
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
                    entity.setAge(employee.getAge());
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
    public List<Employee> findUserByName(String name){
        return sqlRepository.findUserByName(name);
    }

    @Override
    public List<Employee> findAdultUser(Boolean isAdult) {
        return jpqlRepository.findAdultUser(isAdult);
    }

    @Override
    public List<Employee> findEmployeeByCountry(String country) {
        return jpqlRepository.findEmployeeByCountry(country);
    }

    @Override
    public List<Employee> findEmployeeByEmail(String email) {
        return sqlRepository.findEmployeeByEmail(email);
    }

    @Override
    public Employee hideEmployee(Integer id) {
        Employee employee = repository.findById(id)
                .orElseThrow(ResourceWasDeletedException::new);
        employee.setIsDeleted(true);
        repository.save(employee);
        return employee;
    }

    @Override
    public List<Employee> findAllByIsDeleted(Boolean isDeleted) {
        return sqlRepository.findAllByIsDeleted(isDeleted);
    }

    @Override
    public Employee updateNameById(Integer id, String name) {
        Employee employee = repository.findById(id)
                .orElseThrow(ResourceWasDeletedException::new);
        employee.setName(name);
        repository.save(employee);
        return employee;
    }

    @Override
    public Employee updateCountryById(Integer id, String country) {
        Employee employee = repository.findById(id)
                .orElseThrow(ResourceWasDeletedException::new);
        employee.setCountry(country);
        repository.save(employee);
        return employee;
    }

    @Override
    public Employee updateEmailById(Integer id, String email) {
        Employee employee = repository.findById(id)
                .orElseThrow(ResourceWasDeletedException::new);
        employee.setEmail(email);
        repository.save(employee);
        return employee;
    }


}
