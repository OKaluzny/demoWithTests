package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.JpqlRepository;
import com.example.demowithtests.repository.Repository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JpqlRepositoryTests {

    @Autowired
    private Repository repository;
    @Autowired
    private JpqlRepository jpqlRepository;
    @Test
    public void getAdultEmployeeTest() {
        Employee testEmployee = Employee.builder().isAdult(true).build();
        repository.save(testEmployee);
        List<Employee> employeesTestList = jpqlRepository.findAdultUser(testEmployee.getIsAdult());

        assertThat(employeesTestList.size()).isGreaterThan(0);
        assertEquals(true,employeesTestList.get(0).getIsAdult());
    }

    @Test
    public void getEmployeeByCountryTest() {
        Employee testEmployee = Employee.builder().country("Ukraine").build();
        repository.save(testEmployee);
        List<Employee> employeesTestList = jpqlRepository.findEmployeeByCountry(testEmployee.getCountry());

        assertThat(employeesTestList.size()).isGreaterThan(0);
        assertEquals("Ukraine",employeesTestList.get(0).getCountry());
    }
}
