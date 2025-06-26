package com.example.demowithtests;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Employee Repository Tests")
public class RepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Order(1)
    @Rollback(value = false)
    @DisplayName("Save employee test")
    public void saveEmployeeTest() {

        var employee = Employee.builder()
                .name("Mark")
                .country("England")
                .addresses(new HashSet<>(Set.of(
                        Address
                                .builder()
                                .country("UK")
                                .build())))
                .gender(Gender.M)
                .build();

        employeeRepository.save(employee);

        Assertions.assertThat(employee.getId()).isGreaterThan(0);
        Assertions.assertThat(employee.getId()).isEqualTo(1);
        Assertions.assertThat(employee.getName()).isEqualTo("Mark");
    }

    @Test
    @Order(2)
    @DisplayName("Get employee by id test")
    public void getEmployeeTest() {

        var employee = employeeRepository.findById(1).orElseThrow();

        Assertions.assertThat(employee.getId()).isEqualTo(1);
        Assertions.assertThat(employee.getName()).isEqualTo("Mark");
    }

    @Test
    @Order(3)
    @DisplayName("Get employees test")
    public void getListOfEmployeeTest() {

        var employeesList = employeeRepository.findAll();

        Assertions.assertThat(employeesList.size()).isGreaterThan(0);

    }

    @Test
    @Order(4)
    @Rollback(value = false)
    @DisplayName("Update employee test")
    public void updateEmployeeTest() {

        var employee = employeeRepository.findById(1).orElseThrow();

        employee.setName("Martin");
        var employeeUpdated = employeeRepository.save(employee);

        Assertions.assertThat(employeeUpdated.getName()).isEqualTo("Martin");

    }

    @Test
    @Order(5)
    @DisplayName("Find employee by gender test")
    public void findByGenderTest() {

        var employees = employeeRepository.findByGender(Gender.M.toString(), "UK");

        assertThat(employees.get(0).getGender()).isEqualTo(Gender.M);
    }

    @Test
    @Order(6)
    @Rollback(value = false)
    @DisplayName("Delete employee test")
    public void deleteEmployeeTest() {

        var employee = employeeRepository.findById(1).orElseThrow();

        employeeRepository.delete(employee);

        Employee employeeNull = null;

        var optionalEmployee = Optional.ofNullable(employeeRepository.findByName("Martin"));

        if (optionalEmployee.isPresent()) {
            employeeNull = optionalEmployee.orElseThrow();
        }

        Assertions.assertThat(employeeNull).isNull();
    }

    @Test
    @Order(7)
    @Rollback(value = false)
    public void whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given
        Employee bob = new Employee();
        bob.setName("Bob");
        entityManager.persistAndFlush(bob);
        String newName = "Boob";
        // When
        employeeRepository.updateEmployeeByName(newName, bob.getId());
        // Then
        Employee found = employeeRepository.findByName(newName);
        assertThat(found.getName()).isEqualTo(newName);
    }

    @Test
    public void testFindEmployeeByEmailNotNull() {

        Employee employee1 = new Employee();
        employee1.setName("John");
        employee1.setEmail("john@gmail.com");

        employeeRepository.save(employee1);

        Employee employee2 = new Employee();
        employee2.setName("Jane");
        employee2.setEmail(null);  // not setting email

        employeeRepository.save(employee2);

        Employee foundEmployee = employeeRepository.findEmployeeByEmailNotNull();
        assertNotNull(foundEmployee.getEmail() != null);
    }

    @Test
    @Order(8)
    @Rollback(value = false)
    @DisplayName("Update employee using updateEmployee method test")
    public void testUpdateEmployee() {
        // given
        Employee employee = new Employee();
        employee.setName("Alice");
        employee.setEmail("alice@example.com");
        employee.setCountry("USA");
        employeeRepository.save(employee);

        String newName = "Alicia";
        String newEmail = "alicia@example.com";
        String newCountry = "Canada";

        // when
        Integer updatedRows = employeeRepository.updateEmployee(newName, newEmail, newCountry, employee.getId());

        // then
        assertThat(updatedRows).isEqualTo(1);

        Employee updatedEmployee = employeeRepository.findById(employee.getId()).orElseThrow();
        assertThat(updatedEmployee.getName()).isEqualTo(newName);
        assertThat(updatedEmployee.getEmail()).isEqualTo(newEmail);
        assertThat(updatedEmployee.getCountry()).isEqualTo(newCountry);
    }
}
