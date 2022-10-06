package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

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
     * It returns a page of employees.
     *
     * @param page The page number to be returned.
     * @param size The number of records per page.
     * @param sortList The list of fields to sort by.
     * @param sortOrder The sort order, either "asc" or "desc".
     * @return A Page<Employee> object.
     */
    Page<Employee> findAllByPage(int page, int size, List<String> sortList, String sortOrder);

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
     * @param id The id of the employee you want to update.
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
     * Removes all of the elements from this list
     */
    void removeAll();

    /**
     * Find all employees with the given name.
     *
     * @param name The name of the method you want to create in the repository.
     * @return A list of employees with the name specified.
     */
    List<Employee> findUserByName(String name);
    /**
     * Find all employees with the given name, and return them in a page of size size, sorted by the given sortList in the
     * given sortOrder.
     *
     * @param name The name of the employee to search for.
     * @param page The page number to retrieve.
     * @param size The number of records per page.
     * @param sortList A list of fields to sort by.
     * @param sortOrder The sort order, either "asc" or "desc".
     * @return Page<Employee>
     */
    Page<Employee> findByName(String name, int page, int size, List<String> sortList, String sortOrder);

    /**
     * Find all employees who are adults.
     *
     * @param isAdult Boolean
     * @return A list of employees
     */
    List<Employee> findAdultUser(Boolean isAdult);

    /**
     * Find all employees who live in the given country.
     *
     * @param country The country to search for.
     * @return A list of employees that are from the country specified.
     */
    List<Employee> findEmployeeByCountry(String country);
    /**
     * "Find all employees in a given country, and return them in a pageable format."
     *
     * The first parameter is the country to search for. The second and third parameters are the page number and page size.
     * The fourth parameter is a list of sort fields, and the fifth parameter is the sort order
     *
     * @param country The country to search for
     * @param page The page number to retrieve.
     * @param size The number of records per page.
     * @param sortList A list of fields to sort by.
     * @param sortOrder "asc" or "desc"
     * @return Page<Employee>
     */
    Page<Employee> findByCountry(String country, int page, int size, List<String> sortList, String sortOrder);

    /**
     * Find all employees with the given email address.
     *
     * @param email The email address of the employee to find.
     * @return A list of employees with the given email.
     */
    List<Employee> findEmployeeByEmail(String email);

    /**
     * Hide an employee by id.
     *
     * @param id The id of the employee to be hidden.
     * @return The Employee object that was hidden.
     */
    Employee hideEmployee(Integer id);

    /**
     * Find all employees where isDeleted is true.
     *
     * @param isDeleted This is the column name in the database.
     * @return A list of all employees that have been deleted.
     */
    List<Employee> findAllByIsDeleted(Boolean isDeleted);

    /**
     * Update the name of the employee with the given id.
     *
     * @param id The id of the employee to update
     * @param name The name of the method
     * @return Employee
     */
    Employee updateNameById(Integer id, String name);

    /**
     * Update the country of the employee with the given id.
     *
     * @param id The id of the employee you want to update.
     * @param country The country to update the employee with.
     * @return Employee
     */
    Employee updateCountryById(Integer id, String country);
    /**
     * Update the email of the employee with the given id.
     *
     * @param id The id of the employee to update
     * @param email The email address of the employee.
     * @return Employee
     */
    Employee updateEmailById(Integer id, String email);
    /**
     * Update the age of the employee with the given id.
     *
     * @param id The id of the employee to update
     * @param age The age of the employee.
     * @return Employee
     */
    Employee updateAgeById(Integer id, Integer age);

    /**
     * Get a list of employee names sorted by country.
     *
     * @return A list of countries.
     */
    List<String> getEmployeeSortCountry();
    /**
     * Returns a list of emails, or an empty list if there are no emails.
     *
     * @return Optional<String>
     */
    Optional<String> getEmail();
    /**
     * GetAge returns a list of integers.
     *
     * @return An ArrayList of Integers
     */
    List<Integer> getAge();
    /**
     * Get an employee by age and by email.
     *
     * @return Optional<Employee>
     */
    Optional<Employee> getEmployeeByAgeAndByEmail();
}