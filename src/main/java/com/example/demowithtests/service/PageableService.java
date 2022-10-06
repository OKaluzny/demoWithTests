package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PageableService {

    /**
     * Get all employees with pagination.
     *
     * @param pageable This is the object that contains the information about the page number, page size, and sorting
     * order.
     * @return A Page<Employee> object.
     */
    Page<Employee> getAllWithPagination(Pageable pageable);

    /**
     * It returns a Page of Employee objects, where the page number is page, the page size is size, the sort order is
     * sortOrder, and the sort list is sortList
     *
     * @param page The page number.
     * @param size The number of records per page.
     * @param sortList A list of fields to sort by.
     * @param sortOrder The sort order. Can be either "asc" or "desc".
     * @return Page<Employee>
     */
    Page<Employee> findUsersWithPhoneNumberPageable(int page, int size,
                                                    List<String> sortList, String sortOrder);

    /**
     * It returns a page of employees with the given name, sorted by the given sortList and sortOrder.
     *
     * @param name The name of the employee you want to search for.
     * @param page The page number
     * @param size The number of records per page.
     * @param sortList A list of fields to sort by.
     * @param sortOrder The sort order. Can be either "asc" or "desc".
     * @return Page<Employee>
     */
    Page<Employee> findAllByNamePagination(String name, int page, int size,
                                          List<String> sortList, String sortOrder);

    /**
     * @param country   Filter for the country if required
     * @param page      number of the page returned
     * @param size      number of entries in each page
     * @param sortList  list of columns to sort on
     * @param sortOrder sort order. Can be ASC or DESC
     * @return Page object with customers after filtering and sorting
     */
    Page<Employee> findByCountryContaining(String country, int page, int size,
                                           List<String> sortList, String sortOrder);

    /**
     * > Find all employees with a given email address, and return them in a pageable format
     *
     * @param page The page number
     * @param size The number of records to be returned in a page.
     * @param sortList A list of fields to sort by.
     * @param sortOrder The sort order, either "asc" or "desc".
     * @return A Page<Employee> object.
     */
    Page<Employee> findEmployeesByGmail (int page, int size,
                                         List<String> sortList, String sortOrder);

}
