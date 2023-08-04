package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeServiceEMBean implements EmployeeServiceEM {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @param employee
     * @return
     */
    @Override
    @Transactional //jakarta
    public Employee createWithJpa(Employee employee) {
        return entityManager.merge(employee);
    }

    /**
     * @param id
     */
    @Override
    @Transactional //jakarta
    public void deleteByIdWithJpa(Integer id) {
        Employee employee = entityManager.find(Employee.class, id);
        entityManager.remove(employee);
    }

    /**
     * @param id
     * @param employee
     * @return
     */
    @Override
    @Transactional //jakarta
    public Employee updateByIdWithJpa(Integer id, Employee employee) {
        Employee refreshEmployee = entityManager.find(Employee.class, id);
        return entityManager.merge(refreshEmployee);

    }

    /**
     * @return
     */
    @Override
    @Transactional //jakarta
    public Set<String> findAllCountriesWithJpa() {
        return entityManager.createQuery("select distinct country from Employee", String.class).getResultStream().collect(Collectors.toSet());
    }
}
