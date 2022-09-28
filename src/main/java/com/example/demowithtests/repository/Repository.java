package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

    List<Employee> findAllByName(String name);

    @Query(value = "Select * from users where phone_number is not null and is_deleted=false", nativeQuery = true)
    List<Employee> findUsersWithPhoneNumber();

    @Query("Select user from Employee user where user.email is null AND user.isDeleted = false")
    List<Employee> findRecordsWhereEmailNull();

    @Query("Select user from Employee user where user.country = ?1 AND user.isDeleted = false")
    List<Employee> findEmployeesByCountry(String country);

    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findByName(String name, Pageable pageable);

    Page<Employee> findByCountryContaining(String country, Pageable pageable);

    @Query(value = "SELECT * FROM users u WHERE u.phone_number IS NOT NULL AND is_deleted=false", nativeQuery = true)
    Page<Employee> findUsersWithPhoneNumberPageable(Pageable pageable);
}
