package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@org.springframework.stereotype.Repository
//@Component
public interface Repository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

    List<Employee> findAllByName(String name);

    @Query(value = "Select * from users where phone_number is not null", nativeQuery = true)
    List<Employee> findUsersWithPhoneNumber();

    @Query("Select user from Employee user where user.email is null")
    List<Employee> findRecordsWhereEmailNull();

    @Query("Select user from Employee user where user.country = ?1")
    List<Employee> findEmployeesByCountry(String country);
}
