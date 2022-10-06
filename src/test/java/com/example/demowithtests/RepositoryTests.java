package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.Repository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RepositoryTests {

    @Autowired
    private Repository repository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveEmployeeTest() {

        Employee employee = Employee.builder().name("Mark").country("England").build();

        repository.save(employee);

        assertThat(employee.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getEmployeeTest() {

        Employee employee = repository.findById(1).orElseThrow();

        assertThat(employee.getId()).isEqualTo(1);
        assertEquals("Mark", employee.getName());

    }

    @Test
    @Order(3)
    public void getListOfEmployeeTest() {

        List<Employee> employeesList = repository.findAll();

        assertThat(employeesList.size()).isGreaterThan(0);

    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateEmployeeTest() {

        Employee employee = repository.findById(1).get();

        employee.setName("Martin");
        Employee employeeUpdated = repository.save(employee);

        assertThat(employeeUpdated.getName()).isEqualTo("Martin");
        assertThat(employee.getName()).isEqualTo(repository.findById(1).orElseThrow().getName());

    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteEmployeeTest() {

        Employee employee = repository.findById(1).get();

        repository.delete(employee);

        //repository.deleteById(1L);

        Employee employee1 = null;

        Optional<Employee> optionalAuthor = Optional.ofNullable(repository.findByName("Martin"));

        if (optionalAuthor.isPresent()) {
            employee1 = optionalAuthor.get();
        }

        assertThat(employee1).isNull();
    }

    @Test
    @Order(6)
    public void getListOfEmployeeWithPhoneNumberTest() {
        Long phoneNumber = 12345678L;
        Employee employee = new Employee();
        employee.setPhoneNumber(phoneNumber);

        repository.save(employee);

        List<Employee> employeesList = repository.findUsersWithPhoneNumber();

        assertThat(employeesList.size()).isGreaterThan(0);
        assertEquals(phoneNumber, employeesList.get(0).getPhoneNumber());
        assertThat(repository.findById(2).orElseThrow().getPhoneNumber()).isSameAs(phoneNumber);

    }

    @Test
    @Order(7)
    public void getEmployeesByCountryTest() {
        String country = "Ukraine";
        Employee employee = new Employee();
        employee.setCountry(country);

        repository.save(employee);

        List<Employee> employeesList = repository.findEmployeesByCountry(country);

        assertThat(employeesList.size()).isGreaterThan(0);
        assertEquals(country, employeesList.get(0).getCountry());
        assertThat(repository.findById(3).orElseThrow().getCountry()).isSameAs(country);

    }

    @Test
    @Order(8)
    public void getEmployeesWithNameTest() {
        String name = "Kenny";
        Employee employee = new Employee();
        employee.setName(name);

        repository.save(employee);

        List<Employee> employeesList = repository.findAllByName(name);

        assertThat(employeesList.size()).isGreaterThan(0);
        assertEquals(name, employeesList.get(0).getName());
        assertThat(repository.findById(4).orElseThrow().getName()).isSameAs(name);

    }

    @Test
    @Order(9)
    public void getEmployeesWithNullEmailTest() {
        Employee employee = new Employee();
        employee.setEmail(null);

        repository.save(employee);

        List<Employee> employeesList = repository.findRecordsWhereEmailNull();

        assertThat(employeesList.size()).isGreaterThan(0);
        assertNull(employeesList.get(0).getEmail());
        assertThat(repository.findById(5).orElseThrow().getEmail()).isEqualTo(null);

    }
}
