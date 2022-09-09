package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Repository
//@Component
public interface Repository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

    // №1 - find users by name
    @Query(value = "select * from users where name = ?1", nativeQuery = true)
    List<Employee> getName(String name);

    // №2 - update column access (analog is deleted)
    @Modifying
    @Transactional
    @Query("update Employee e set e.access = true where e.id = ?1")
    void isAccess(Integer id);

    // and return list where access = true
    @Query("select e from Employee e where e.access = true")
    List<Employee> getIsAccess();

    // №3 - update column hour work employee
    @Modifying
    @Transactional
    @Query(value = "update users set hour = ?2 where id = ?1", nativeQuery = true)
    void updateHour(Integer id, Double hour);

    // №4 - salary by id
    @Modifying
    @Transactional
    @Query("update Employee e set e.salary = ?2 where e.id = ?1")
    void getSalary(Integer id, Double salary);

    // get list employee name and employee salary
    @Query("select e.name, e.salary from Employee e")
    List<Object> listSalary();

}
