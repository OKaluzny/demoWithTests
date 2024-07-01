package com.example.demowithtests;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Document;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Employee Repository Tests")
public class RepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Order(8)
    public void getEmployeeWithDocumentsTest() {
        Employee mockEmployee = new Employee();
        Document mockDocument = new Document();
        mockDocument.setId(2);
        mockDocument.setNumber("12345");
        mockDocument.setUuid("uuid-12345");
        mockDocument.setIsHandled(true);

        mockEmployee.setId(2);
        mockEmployee.setName("John Doe");
        mockEmployee.setCountry("USA");
        mockEmployee.setEmail("john.doe@example.com");
        mockEmployee.setDocument(mockDocument);

        employeeRepository.save(mockEmployee);

        var employee = employeeRepository.findById(2).orElseThrow();

        assertThat(employee.getId()).isEqualTo(2);
        assertThat(employee.getName()).isEqualTo("John Doe");
        assertThat(employee.getCountry()).isEqualTo("USA");
        assertThat(employee.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(employee.getDocument().getNumber()).isEqualTo("12345");
        assertThat(employee.getDocument().getUuid()).isEqualTo("uuid-12345");
        assertThat(employee.getDocument().getIsHandled()).isEqualTo(true);
    }

    @Test
    @Order(9)
    public void findAllActiveTest(){
        var employeesList = employeeRepository.findAllActive();
        Assertions.assertThat(employeesList.size()).isGreaterThan(0);
    }
    @Test
    @Order(10)
    public void findActiveByIdTest(){
        var employee = employeeRepository.findActiveById(1);
        Assertions.assertThat(employee).isNotNull();
    }
    @Test
    @Order(11)
    public void softDeleteTest(){
        var employee = employeeRepository.findById(2).orElseThrow();
        employee.setIsDeleted(true);
        employeeRepository.save(employee);

        employee = employeeRepository.findById(2).orElseThrow();
        assertThat(employee.getIsDeleted()).isEqualTo(true);
    }


    @Test
    @Order(1)
    @Rollback(value = false)
    @DisplayName("Save employee test")
    public void saveEmployeeTest() {

        var employee = Employee.builder()
                .name("Mark")
                .country("England")
                .email("mark@example.com")
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
        bob.setEmail("bob@example.com");
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

        Employee foundEmployee = employeeRepository.findEmployeeByEmailNotNull();
        assertNotNull(foundEmployee.getEmail());
    }

}
