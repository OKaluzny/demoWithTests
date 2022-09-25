package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.createDto.EmployeeDto;
import com.example.demowithtests.dto.readDto.*;
import com.example.demowithtests.dto.updateDto.*;
import com.example.demowithtests.repository.JpqlRepository;
import com.example.demowithtests.repository.Repository;
import com.example.demowithtests.repository.SqlRepository;
import com.example.demowithtests.util.config.mapstruct.EmployeeDtoMapper;
import com.example.demowithtests.util.exeption.ResourceWasDeletedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@AllArgsConstructor
@Slf4j
@org.springframework.stereotype.Service
public class ServiceBean implements Service {

    private final Repository repository;
    private final SqlRepository sqlRepository;
    private final JpqlRepository jpqlRepository;

    private final EmployeeDtoMapper mapper = Mappers.getMapper(EmployeeDtoMapper.class);

    @Override
    public EmployeeDto create(Employee employee) {
        employee.setIsAdult(employee.getAge() >= 18);
        return mapper.createEmployeeDto(repository.save(employee));
    }

    @Override
    public List<EmployeeReadAllDto> getAll() {
        return (List<EmployeeReadAllDto>) mapper.getAllEmployeeDto(repository.findAll());
    }

    @Override
    public EmployeeReadDto getById(Integer id) {
        return mapper.readByIdEmployeeDto(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public EmployeeUpdateDto updateById(Integer id, Employee employee) {
        return mapper.updateByIdEmployeeDto(repository.findById(id)
                .map(entity -> {
                    entity.setName(employee.getName());
                    entity.setEmail(employee.getEmail());
                    entity.setCountry(employee.getCountry());
                    entity.setAge(employee.getAge());
                    return repository.save(entity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id)));
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
    public List<EmployeeReadAllByNameDto> findUserByName(String name){

        return (List<EmployeeReadAllByNameDto>) mapper.getAllByNameEmployeeDto(sqlRepository.findUserByName(name));
    }

    @Override
    public List<EmployeeReadAllByIsAdultDto> findAdultUser(Boolean isAdult) {
        return (List<EmployeeReadAllByIsAdultDto>) mapper.getAllByIsAdultEmployeeDto(jpqlRepository.findAdultUser(isAdult));
    }

    @Override
    public List<EmployeeReadAllByCountryDto> findEmployeeByCountry(String country) {
        return (List<EmployeeReadAllByCountryDto>) mapper.getAllByCountryEmployeeDto(jpqlRepository.findEmployeeByCountry(country));
    }

    @Override
    public List<EmployeeReadAllByGmailDto> findEmployeeByEmail(String email) {
        return (List<EmployeeReadAllByGmailDto>) mapper.getAllByGmailEmployeeDto(sqlRepository.findEmployeeByEmail(email));
    }

    @Override
    public EmployeeUpdateIsDeletedDto hideEmployee(Integer id) {
        return null;
    }

//    @Override
//    public EmployeeUpdateIsDeletedDto hideEmployee(Integer id) {
//        return mapper.updateIsDeletedByIdEmployeeDto(repository.findById(id)
//                .map(entity ->{
//                    entity.setIsDeleted(true);
//                })
//                .orElseThrow(ResourceWasDeletedException::new));
//    }

    @Override
    public List<EmployeeReadAllByIsDeletedDto> findAllByIsDeleted(Boolean isDeleted) {
        return (List<EmployeeReadAllByIsDeletedDto>) mapper.updateIsDeletedByIdEmployeeDto((Employee) sqlRepository.findAllByIsDeleted(isDeleted));
    }

    @Override
    public EmployeeUpdateNameDto updateNameById(Integer id, String name) {
        return mapper.updateNameByIdEmployeeDto(repository.findById(id)
                .orElseThrow(ResourceWasDeletedException::new));
        EmployeeUpdateNameDto.setName(name);
        repository.save(employee);
    }

    @Override
    public EmployeeUpdateCountryDto updateCountryById(Integer id, String country) {
        Employee employee = repository.findById(id)
                .orElseThrow(ResourceWasDeletedException::new);
        employee.setCountry(country);
        repository.save(employee);
        return employee;
    }

    @Override
    public EmployeeUpdateEmailDto updateEmailById(Integer id, String email) {
        Employee employee = repository.findById(id)
                .orElseThrow(ResourceWasDeletedException::new);
        employee.setEmail(email);
        repository.save(employee);
        return employee;
    }

    @Override
    public EmployeeUpdateAgeDto updateAgeById(Integer id, Integer age) {
        Employee employee = repository.findById(id)
                .orElseThrow(ResourceWasDeletedException::new);
        employee.setAge(age);
        repository.save(employee);
        return employee;
    }


}
