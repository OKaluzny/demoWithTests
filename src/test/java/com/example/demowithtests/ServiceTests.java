package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.EmployeeServiceBean;
import com.example.demowithtests.util.exception.EmployeeNotFoundException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Employee Service Tests")
public class ServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceBean service;

    private Employee employee;
    private List<Employee> employeeList;

    @BeforeEach
    void setUp() {
        employee = Employee
                .builder()
                .id(1)
                .name("Mark")
                .country("UK")
                .email("test@mail.com")
                .gender(Gender.M)
                .build();

        employeeList = List.of(
                Employee.builder()
                        .id(2)
                        .name("isDeleted")
                        .isDeleted(false)
                        .build(),
                Employee.builder()
                        .name("NullEmail")
                        .country("lowerCaseCountry")
                        .isDeleted(false)
                        .email(null)
                        .addresses(null)
                        .gender(null)
                        .build()
        );
    }

    @Test
    @DisplayName("Save employee test")
    public void whenSaveEmployee_shouldReturnEmployee() {

        when(employeeRepository.save(ArgumentMatchers.any(Employee.class))).thenReturn(employee);
        var created = service.create(employee);
        assertThat(created.getName()).isSameAs(employee.getName());
        verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("Get employee by exist id test")
    public void whenGivenId_shouldReturnEmployee_ifFound() {

        Employee employee = new Employee();
        employee.setId(88);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        Employee expected = service.getById(employee.getId());
        assertThat(expected).isSameAs(employee);
        verify(employeeRepository).findById(employee.getId());
    }

    @Test
    @DisplayName("Throw exception when employee not found test")
    public void should_throw_exception_when_employee_doesnt_exist() {

        when(employeeRepository.findById(anyInt())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> employeeRepository.findById(anyInt()));
    }

    @Test
    @DisplayName("Read employee by id test")
    public void readEmployeeByIdTest() {

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        Employee expected = service.getById(employee.getId());
        assertThat(expected).isSameAs(employee);
        verify(employeeRepository).findById(employee.getId());
    }

    @Test
    @DisplayName("Read all employees test")
    public void readAllEmployeesTest() {

        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        var list = employeeRepository.findAll();
        assertThat(list.size()).isGreaterThan(0);
        verify(employeeRepository).findAll();
    }

    @Test
    @DisplayName("Delete employee test")
    public void deleteEmployeeTest() {
        Employee employeeDeleted = employeeList.get(0);

        when(employeeRepository.findById(employeeDeleted.getId())).thenReturn(Optional.of(employeeDeleted));
        when(employeeRepository.save(employeeDeleted)).thenReturn(employeeDeleted);

        assertThat(service.getById(employeeDeleted.getId()).isDeleted()).isFalse();

        service.removeById(employeeDeleted.getId());

        assertThrows(EmployeeNotFoundException.class, () -> service.getById(employeeDeleted.getId()));

        verify(employeeRepository).save(employeeDeleted);
    }

    @Test
    @DisplayName("Filter employees with 'null' email test")
    void filterNullEmailsTest() {

        when(employeeRepository.findAllByEmailNull()).thenReturn(employeeList);
        List<Employee> nullEmails = service.filterNullEmails();
        assertThat(nullEmails).isNotEmpty();
        assertThat(nullEmails.get(1).getEmail()).isNull();
        assertThat(nullEmails.get(1).getName()).isEqualTo("NullEmail");
        verify(employeeRepository).findAllByEmailNull();
    }

    @Test
    @DisplayName("Filter employees with 'null' email exception test")
    void filterNullEmailsThrowsTest() {

        when(employeeRepository.findAllByEmailNull()).thenReturn(Collections.emptyList());
        assertThrows(EntityNotFoundException.class, () -> service.filterNullEmails());
    }

    @Test
    @DisplayName("Filter Employees with Lower Case Countries test")
    void filterLowerCaseCountriesTest() {

        when(employeeRepository.findAllLowerCaseCountries()).thenReturn(employeeList);
        List<Employee> lowerCaseCountries = service.filterLowerCaseCountries();
        assertThat(lowerCaseCountries).isNotEmpty();
        assertThat(lowerCaseCountries.get(1).getCountry().charAt(0)).isLowerCase();
        verify(employeeRepository).findAllLowerCaseCountries();
    }

    @Test
    @DisplayName("Filter Employees with Lower Case Countries exception test")
    void filterLowerCaseCountriesThrowsTest() {

        when(employeeRepository.findAllLowerCaseCountries()).thenReturn(Collections.emptyList());
        assertThrows(EntityNotFoundException.class, () -> service.filterLowerCaseCountries());
    }
}
