package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    //@Query(value = "SELECT * FROM users", nativeQuery = true)

    @Query(value = "select e from Employee e where e.country =?1")
    List<Employee> findByCountry(String country);

    @Query(value = "select * from users join addresses on users.id = addresses.employee_id " +
            "where users.gender = :gender and addresses.country = :country", nativeQuery = true)
    List<Employee> findByGender(String gender, String country);

    Employee findByName(String name);

    Employee findEmployeeByEmailNotNull();

    @NotNull
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findByName(String name, Pageable pageable);

    Page<Employee> findByCountryContaining(String country, Pageable pageable);

    List<Employee> findAllByEmailNull();

    @Query(value = "select * from users where country ~ '^[a-z]|^[а-я]'", nativeQuery = true)
    List<Employee> findAllLowerCaseCountries();

    @Modifying
    @Transactional
    @Query(value = "update users " +
            "set country = regexp_replace(country, left(country, 1), upper(left(country, 1))) " +
            "where country ~ '^[a-z]|^[а-я]'", nativeQuery = true)
    void updateLowerCaseCountriesToUpperCase();
}
