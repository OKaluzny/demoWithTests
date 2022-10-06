package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Employee, Integer> {

    /**
     * Find an employee by name.
     *
     * @param name The name of the method.
     * @return Employee
     */
    Employee findByName(String name);

    /**
     * Find all employees whose name is equal to the given name.
     *
     * @param name The name of the method.
     * @return A list of employees with the name specified.
     */
    List<Employee> findAllByName(String name);

    /**
     * Find all users who have a phone number and are not deleted
     *
     * @return List of Employee objects
     */
    @Query(value = "Select * from users where phone_number is not null and is_deleted=false", nativeQuery = true)
    List<Employee> findUsersWithPhoneNumber();

    /**
     * Find all records where the email field is null and the isDeleted field is false
     *
     * @return A list of all employees where the email is null and isDeleted is false.
     */
    @Query("Select user from Employee user where user.email is null AND user.isDeleted = false")
    List<Employee> findRecordsWhereEmailNull();

    /**
     * Find all employees who are not deleted and whose country is equal to the country parameter.
     *
     * @param country The country to search for.
     * @return List of Employee objects
     */
    @Query("Select user from Employee user where user.country = ?1 AND user.isDeleted = false")
    List<Employee> findEmployeesByCountry(String country);
}
