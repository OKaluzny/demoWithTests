package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@org.springframework.stereotype.Repository
@Component
public interface JpqlRepository extends JpaRepository<Employee, Integer> {

    //Получение совершеннолетних юзеров
    @Query("SELECT user FROM Employee user WHERE user.isAdult = true ") //jpql
    List<Employee> findAdultUser(Boolean isAdult);

    //Получение юзеров по стране
    @Query("SELECT user FROM Employee user WHERE user.country = ?1") //jpql
    List<Employee> findEmployeeByCountry(String country);
}