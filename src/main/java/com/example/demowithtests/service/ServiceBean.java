package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.JpqlRepository;
import com.example.demowithtests.repository.Repository;
import com.example.demowithtests.repository.SqlRepository;
import com.example.demowithtests.util.exeption.ResourceWasDeletedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Page<Employee> findAllByPage(int page, int size, List<String> sortList, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        return jpqlRepository.findAllByPage(pageable);
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
    public Page<Employee> findByName(String name, int page, int size, List<String> sortList, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        return jpqlRepository.findByName(name,pageable);
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
    public Page<Employee> findByCountry(String country, int page, int size, List<String> sortList, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        return jpqlRepository.findByCountry(country,pageable);
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

    @Override
    public Employee updateAgeById(Integer id, Integer age) {
        Employee employee = repository.findById(id)
                .orElseThrow(ResourceWasDeletedException::new);
        employee.setAge(age);
        repository.save(employee);
        return employee;
    }

    @Override
    public List<String> getEmployeeSortCountry() {
        var employeeCountryList = jpqlRepository.findCountry();
        return employeeCountryList.stream()
                .filter(c->c.startsWith("U"))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<String> getEmail() {
        var employeeEmailList = jpqlRepository.findEmail();
        var emails = employeeEmailList.stream()
                .filter(e->e.endsWith("gmail.com"))
                .findFirst()
                .orElse("error?");
        return Optional.of(emails);
    }

    @Override
    public List<Integer> getAge() {
        var employeeAgeList = jpqlRepository.findAge();
        var age = employeeAgeList.stream()
                .filter(a->a >= 21)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        return age;

    }

    @Override
    public Optional<Employee> getEmployeeByAgeAndByEmail() {
        var employeeList = sqlRepository.findAll();
        var ageAndEmails = employeeList.stream()
                .filter(e->e.getEmail().endsWith("gmail.com") && e.getAge()>=21)
                .findAny()
                .orElse(Employee.builder().build());
        return Optional.of(ageAndEmails);
    }

    /**
     * It takes a list of strings and a string and returns a list of Sort.Order objects
     *
     * @param sortList A list of strings that represent the fields to sort by.
     * @param sortDirection The direction of the sort.
     * @return A list of Sort.Order objects.
     */
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