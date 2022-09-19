package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.Repository;
import com.example.demowithtests.repository.SqlRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SqlRepositoryTests {

    @Autowired
    private Repository repository;
    @Autowired
    private  SqlRepository sqlRepository;

    @Test
    public void getEmployeeByNameTest() {
        Employee testEmployee = Employee.builder().name("Max").build();
        repository.save(testEmployee);
        List<Employee> employeesTestList = sqlRepository.findUserByName(testEmployee.getName());

        assertThat(employeesTestList.size()).isGreaterThan(0);
        assertEquals("Max",employeesTestList.get(0).getName());
    }

    @Test
    public void getGmailUsersTest() {
        Employee testEmployee = Employee.builder().email("max@gmail.com").build();
        repository.save(testEmployee);
        List<Employee> employeesTestList = sqlRepository.findEmployeeByEmail(testEmployee.getEmail());

        assertThat(employeesTestList.size()).isGreaterThan(0);
        assertEquals("max@gmail.com",employeesTestList.get(0).getEmail());
    }

    @Test
    public void getHideEmployeeTest() {
        Employee testEmployee = Employee.builder().isDeleted(true).build();
        repository.save(testEmployee);
        List<Employee> employeesTestList = sqlRepository.findAllByIsDeleted(testEmployee.getIsDeleted());

        assertThat(employeesTestList.size()).isGreaterThan(0);
        assertEquals(true,employeesTestList.get(0).getIsDeleted());
    }

}
