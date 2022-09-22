package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.Repository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

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

        Assertions.assertThat(employee.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getEmployeeTest() {

        Employee employee = repository.findById(1).orElseThrow();

        Assertions.assertThat(employee.getId()).isEqualTo(1);

    }

    @Test
    @Order(3)
    public void getListOfEmployeeTest() {

        List<Employee> employeesList = repository.findAll();

        Assertions.assertThat(employeesList.size()).isGreaterThan(0);

    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateEmployeeTest() {

        Employee employee = repository.findById(1).get();

        employee.setName("Martin");
        Employee employeeUpdated = repository.save(employee);

        Assertions.assertThat(employeeUpdated.getName()).isEqualTo("Martin");

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

        Assertions.assertThat(employee1).isNull();
    }

    @Test
    @Order(6)
    public void listNameEmployeeTest() {

        Employee employee = new Employee();
        employee.setName("Vlad");

        repository.save(employee);

        List<Employee> employeesList = repository.getName(employee.getName());

        Assertions.assertThat(employeesList.size()).isGreaterThan(0);
    }

    @Test
    @Order(7)
    public void listCountryEmployeeTest() {

        Employee employee = new Employee();
        employee.setId(55);
        employee.setName("Vlad");
        employee.setCountry("Ukraine");

        repository.save(employee);

        List<Employee> employeesList = repository.getListCountry(employee.getCountry());

        Assertions.assertThat(employeesList.size()).isGreaterThan(0);
    }

    @Test
    @Order(8)
    @Rollback(value = false)
    public void updateEmailTest() {

        Employee employee = new Employee();
        employee.setName("Vlad");
        employee.setEmail("email");

        repository.save(employee);

        employee.setEmail("emailTest@.com");
        Employee employeeUpdated = repository.save(employee);

        Assertions.assertThat(employeeUpdated.getEmail()).isEqualTo("emailTest@.com");

    }

//    @Test
//    @Order(9)
//    @Rollback(value = false)
//    public void isAccessTest() {
//
//        Employee employee = new Employee();
//        employee.setId(8);
//        employee.setName("Vlad");
//
//        repository.save(employee);
//        repository.isAccess(8);
//        Employee employeeUpdated = repository.save(employee);
//
//        Assertions.assertThat(employeeUpdated.isAccess()).isEqualTo(true);
//
//    }
}
