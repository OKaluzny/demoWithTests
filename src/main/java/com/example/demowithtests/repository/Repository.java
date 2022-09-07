package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@org.springframework.stereotype.Repository
//@Component
public interface Repository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

    @Query(value = "SELECT * FROM users WHERE name = ?1", nativeQuery = true)
    List<Employee> findUserByName(String name);

    @Query("SELECT user FROM Employee user WHERE user.isAdult = true ")
    List<Employee> findAdultUser(Boolean isAdult);

    @Query("SELECT user FROM Employee user WHERE user.country = ?1")
    List<Employee> findEmployeeByCountry(String country);

//    @Query("SELECT user FROM Employee user WHERE user.country = ?1")
//    List<Employee> findEmployeeByEmail();
}
