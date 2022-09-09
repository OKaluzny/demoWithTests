package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@org.springframework.stereotype.Repository
@Component
public interface SqlRepository extends JpaRepository<Employee, Integer>{
    @Query(value = "SELECT * FROM users WHERE name = ?1", nativeQuery = true) //sql
    List<Employee> findUserByName(String name);

    @Query(value ="SELECT * FROM users WHERE email LIKE '%gmail.com'", nativeQuery = true) //sql
    List<Employee> findEmployeeByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE is_deleted = ?1", nativeQuery = true) //sql
    List<Employee> findAllByIsDeleted(Boolean isDeleted);
}
