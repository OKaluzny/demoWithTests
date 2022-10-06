package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Service {

    /**
     * Create a new employee.
     *
     * @param employee The employee object to be created.
     * @return The employee object that was created.
     */
    Employee create(Employee employee);

    /**
     * Get all employees.
     *
     * @return A list of all the employees in the database.
     */
    List<Employee> getAll();

    /**
     * Get an employee by id.
     *
     * @param id The id of the employee to be retrieved.
     * @return Employee
     */
    Employee getById(Integer id);

    /**
     * Update an employee by id.
     *
     * @param id    The id of the employee you want to update.
     * @param plane The object that will be updated in the database.
     * @return Employee
     */
    Employee updateById(Integer id, Employee plane);

    /**
     * Removes the entity with the given id.
     *
     * @param id The id of the object to be removed.
     */
    void removeById(Integer id);

    /**
     * Removes all the elements from this list
     */
    void removeAll();

    /**
     * Find all employees whose name is equal to the given name.
     *
     * @param name The name of the method.
     * @return A list of employees with the name specified.
     */
    List<Employee> findAllByName(String name);

    /**
     * Find all employees who have a phone number.
     *
     * @return A list of employees with phone numbers.
     */
    List<Employee> findUsersWithPhoneNumber();

    /**
     * Find all employees where the email is null.
     *
     * @return A list of employees where the email is null.
     */
    List<Employee> findRecordsWhereEmailNull();

    /**
     * Find all employees where the email is null.
     *
     * @return List<Employee>
     */
    List<Employee> findRecordsWhereEmailNullStreamApi();

    /**
     * "Find all employees who live in the given country."
     * /**
     * Find all employees in a given country.
     *
     * @param country The country to search for.
     * @return A list of employees that are from the country specified.
     */
    List<Employee> findUsersWithCountryStreamApi(String country);

    /**
     * Find all employees in a given country.
     *
     * @param country The country to search for.
     * @return A list of employees that are from the country specified.
     */
    List<Employee> findEmployeesByCountry(String country);

    /**
     * Update the phone number of the employee with the given id.
     *
     * @param id          The id of the employee you want to update.
     * @param phoneNumber The new phone number for the employee.
     * @return Employee
     */
    Employee updatePhoneById(Integer id, Long phoneNumber);

    /**
     * "Find all employees whose email address contains the string 'mail' and return them as a list."
     * The function is implemented using the Stream API
     *
     * @return A list of employees with the email address "john.doe@mail.com"
     */
    Optional<String> findUsersByMailStreamApi();

    /**
     * Find all countries.
     *
     * @return A set of all the countries in the database.
     */
    Set<String> findAllCountries();
}
