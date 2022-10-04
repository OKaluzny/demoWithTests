package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@org.springframework.stereotype.Repository
@Component
public interface JpqlRepository extends JpaRepository<Employee, Integer> {

    //Получение совершеннолетних юзеров
    @Query("SELECT user FROM Employee user WHERE user.isAdult = true ") //jpql
    /**
     * Find all users where the isAdult field is true and return them as a list of User objects.
     *
     * @param isAdult The name of the parameter.
     * @return List<Employee>
     */
    List<Employee> findAdultUser(Boolean isAdult);

    //Получение юзеров по стране
    @Query("SELECT user FROM Employee user WHERE user.country = ?1") //jpql
    /**
     * Find all employees whose country is equal to the country parameter.
     *
     * @param country The country to search for.
     * @return List of Employee objects
     */
    List<Employee> findEmployeeByCountry(String country);

    @Query("SELECT user.name, user.country, user.age, user.email FROM Employee user WHERE user.name = ?1") //jpql
    /**
     * It returns a Page of Employee objects which satisfy the criteria that the name field is equal to the method
     * parameter name
     *
     * @param name The name of the method.
     * @param pageable This is the Pageable object that contains the page number, page size, and sort order.
     * @return A Page of Employee objects.
     */
    Page<Employee> findByName(String name, Pageable pageable);

    @Query("SELECT user.name, user.country, user.age, user.email FROM Employee user WHERE user.country = ?1") //jpql
    /**
     * It returns a Page of Employee objects where the country is equal to the country passed in as a method parameter
     *
     * @param country The country to search for.
     * @param pageable This is the Pageable object that contains the page number, page size, and sort order.
     * @return A Page of Employee objects.
     */
    Page<Employee> findByCountry(String country, Pageable pageable);
}
