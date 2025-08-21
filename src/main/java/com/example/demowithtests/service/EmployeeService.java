package com.example.demowithtests.service;

import com.example.demowithtests.domain.Document;
import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeService {

    Employee create(Employee employee);

    void createAndSave(Employee employee);

    List<Employee> getAll();

    Page<Employee> getAllWithPagination(Pageable pageable);

    Employee getById(Integer id);

    Employee updateById(Integer id, Employee plane);

    void removeById(Integer id);

    void removeAll();

    //Page<Employee> findByCountryContaining(String country, Pageable pageable);

    /**
     * @param country   Filter for the country if required
     * @param page      number of the page returned
     * @param size      number of entries in each page
     * @param sortList  list of columns to sort on
     * @param sortOrder sort order. Can be ASC or DESC
     * @return Page object with customers after filtering and sorting
     */
    Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder);

    /**
     * Get all the countries of all the employees.
     *
     * @return A list of all the countries that employees are from.
     */
    List<String> getAllEmployeeCountry();

    /**
     * It returns a list of countries sorted by name.
     *
     * @return A list of countries in alphabetical order.
     */
    List<String> getSortCountry();

    Optional<String> findEmails();

    List<Employee> filterByCountry(String country);

    Set<String> sendEmailsAllUkrainian();

    List<Employee> findByNameContaining(String name);

    void updateEmployeeByName(String name, Integer id);

    Page<Employee> checkDuplicateEmails(String email, Pageable pageable);

    Employee setDocument(Integer id, Document document);

    Employee removeDocument(Integer id);

    /**
     * Updates an employee's name, email, and country in the database.
     *
     * @param name    The new name of the employee.
     * @param email   The new email of the employee.
     * @param country The new country of the employee.
     * @param id      The id of the employee to update.
     * @return The number of rows affected by the update operation.
     */
    Integer updateEmployee(String name, String email, String country, Integer id);

    Employee findEmployeeByEmail(String email);
}
