package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@org.springframework.stereotype.Repository
public interface PageableRepository extends JpaRepository<Employee, Integer> {

    /**
     * Find all employees, and return them in a pageable format.
     *
     * @param pageable This is the object that contains the information about the page that we want to retrieve.
     * @return A Page object that contains the content of the requested page.
     */
    Page<Employee> findAll(Pageable pageable);

    /**
     * Find all employees with the given name, and return them in a pageable fashion.
     *
     * @param name The name of the employee to search for.
     * @param pageable This is the Pageable object that contains the page number, page size, and sort order.
     * @return A Page<Employee> object.
     */
    Page<Employee> findByName(String name, Pageable pageable);

    /**
     * Find all employees whose country contains the given country string, and return them in a pageable result.
     *
     * @param country The country to search for.
     * @param pageable This is the Pageable object that contains the page number, page size, and sort order.
     * @return A Page<Employee> object.
     */
    Page<Employee> findByCountryContaining(String country, Pageable pageable);

    /**
     * Find all users with a phone number, and return them in a pageable format.
     *
     * @param pageable This is the Pageable object that contains the page number, page size, and sort order.
     * @return A page of employees with phone numbers.
     */
    @Query(value = "SELECT * FROM users u WHERE u.phone_number IS NOT NULL AND is_deleted=false", nativeQuery = true)
    Page<Employee> findUsersWithPhoneNumberPageable(Pageable pageable);

    /**
     * It returns a page of Employee objects that have a gmail email address and are not deleted
     *
     * @param pageable This is the Pageable object that contains the page number, page size, and sort order.
     * @return A Page of Employee objects.
     */
    @Query("select user.name, user.email from Employee user where user.email LIKE '%gmail.com' and user.isDeleted=false")
    Page<Employee> findEmployeesByGmail(Pageable pageable);
}
