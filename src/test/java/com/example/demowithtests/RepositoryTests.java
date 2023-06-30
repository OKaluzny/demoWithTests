package com.example.demowithtests;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Employee Repository Tests")
public class RepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

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

        employeeRepository.saveAll(addEmployeesIntoDB());
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
    @DisplayName("Find employee by 'null' email test")
    void findByEmailThatNullTest() {
        List<Employee> allByEmailNull = employeeRepository.findAllByEmailNull().orElseThrow();

        assertThat(allByEmailNull).isNotEmpty();
        assertThat(allByEmailNull.get(0).getEmail()).isNull();
        assertThat(allByEmailNull.get(0).getName()).isEqualTo("NullEmail");
    }

    @Test
    @Order(8)
    @DisplayName("Find all employees by lower case country test")
    void findAllLowerCaseCountriesTest() {
        List<Employee> lowerCaseCountries = employeeRepository.findAllLowerCaseCountries().orElseThrow();

        assertThat(lowerCaseCountries).isNotEmpty();
        assertThat(lowerCaseCountries.get(0).getCountry().charAt(0)).isLowerCase();
    }

    @Test
    @Order(9)
    @DisplayName("Update Lower case countries to Upper case")
    void updateLowerCaseCountriesToUpperCaseTest() {
        employeeRepository.updateLowerCaseCountriesToUpperCase();

        List<Employee> lowerCaseCountries = employeeRepository.findAllLowerCaseCountries().orElseThrow();

        assertThat(lowerCaseCountries).isEmpty();
    }

    private List<Employee> addEmployeesIntoDB() {
        return List.of(
                Employee.builder()
                        .name("NullEmail")
                        .country("lowerCaseCountry")
                        .email(null)
                        .addresses(null)
                        .gender(null)
                        .build()
        );
    }
}
