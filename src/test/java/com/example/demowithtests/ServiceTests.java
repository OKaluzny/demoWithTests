package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.Repository;
import com.example.demowithtests.service.Impl.ServiceBean;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTests {

    @Mock
    private Repository repository;

    @InjectMocks
    private ServiceBean service;

    @Test
    @DisplayName("Employee should be saving in this method")
    public void whenSaveEmployee_shouldReturnEmployee() {
        Employee employee = new Employee();
        employee.setName("Mark");

        when(repository.save(ArgumentMatchers.any(Employee.class))).thenReturn(employee);

        Employee created = service.create(employee);

        assertThat(created.getName()).isSameAs(employee.getName());
        verify(repository).save(employee);
    }

    @Test
    @DisplayName("List of Employee with given country should be returned")
    public void whenGivenCountry_shouldReturnListEmployee() {
        String country = "testCountry";
        Employee created = new Employee();
        created.setCountry(country);

        List<Employee> employee = new ArrayList<>();
        employee.add(created);

        when(repository.findEmployeesByCountry(country)).thenReturn(employee);

        List<Employee> expected = service.findEmployeesByCountry(country);

        assertThat(expected.size()).isGreaterThan(0);
        assertEquals(country, expected.get(0).getCountry());
        assertThat(expected).isSameAs(employee);

        verify(repository).findEmployeesByCountry(country);
    }

    @Test
    @DisplayName("List of Employee with given name should be returned")
    public void whenGiveName_shouldReturnListEmployee() {
        String name = "Slava";
        Employee created = new Employee();
        created.setName(name);

        List<Employee> employee = new ArrayList<>();
        employee.add(created);

        when(repository.findAllByName(name)).thenReturn(employee);

        List<Employee> expected = service.findAllByName(name);

        assertThat(expected.size()).isGreaterThan(0);
        assertEquals(name, expected.get(0).getName());
        assertThat(expected).isSameAs(employee);

        verify(repository).findAllByName(name);
    }

    @Test
    @DisplayName("List of Employee with field 'phone number' should be returned")
    public void IfPhoneNumberExists_shouldReturnListEmployee() {
        Long phoneNumber = 12345678L;
        Employee created = new Employee();
        created.setPhoneNumber(phoneNumber);

        List<Employee> employee = new ArrayList<>();
        employee.add(created);

        when(repository.findUsersWithPhoneNumber()).thenReturn(employee);

        List<Employee> expected = service.findUsersWithPhoneNumber();

        assertThat(expected.size()).isGreaterThan(0);
        assertEquals(phoneNumber, expected.get(0).getPhoneNumber());
        assertThat(expected).isSameAs(employee);

        verify(repository).findUsersWithPhoneNumber();
    }

    @Test
    @DisplayName("For each employee with empty email should be set certain email and returned the list of Employee")
    public void IfEmailIsNull_shouldReturnListEmployee_setNewEmail() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("test");
        employee.setEmail(null);

        List<Employee> employeesList = new ArrayList<>();
        employeesList.add(employee);

        when(repository.findRecordsWhereEmailNull()).thenReturn(employeesList);

        String newEmail = "t31@itorg.com";
        employee.setEmail(newEmail);
        when(repository.save(ArgumentMatchers.any(Employee.class))).thenReturn(employee);

        List<Employee> expected = service.findRecordsWhereEmailNull();
        assertEquals(newEmail, expected.get(0).getEmail());
        assertThat(expected).isSameAs(employeesList);

        verify(repository).findRecordsWhereEmailNull();
    }

    @Test
    @DisplayName("Employee with certain id should be returned if it's exist")
    public void whenGivenId_shouldReturnEmployee_ifFound() {
        Integer id = 88;
        Employee employee = new Employee();
        employee.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(employee));

        Employee expected = service.getById(id);

        assertEquals(id, expected.getId());
        assertThat(expected).isSameAs(employee);
        verify(repository).findById(id);
    }

    @Test(expected = ResourceNotFoundException.class)
    @DisplayName("ResourceNotFoundException should be appear when employee does not exist")
    public void should_throw_exception_when_employee_doesnt_exist() {
        Integer id = 89;
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName("Mark");

        given(repository.findById(anyInt())).willReturn(Optional.empty());
        service.getById(id);
    }

    @Test
    @DisplayName("Update phone number of employee with certain id")
    public void update_phoneNumberOfEmployeeById_returnEmployee() {
        Integer id = 20;
        Long phoneNumber = 12345678L;
        Employee employee = new Employee();
        employee.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(employee));
        employee.setPhoneNumber(phoneNumber);

        repository.save(employee);

        Employee expected = service.updatePhoneById(id, phoneNumber);

        assertEquals(phoneNumber, expected.getPhoneNumber());
        assertThat(expected).isSameAs(employee);
        verify(repository).findById(id);
    }
}
