package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.Repository;
import com.example.demowithtests.service.ServiceBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    public void whenSaveEmployee_shouldReturnEmployee() {
        Employee employee = new Employee();
        employee.setName("Mark");

        when(repository.save(ArgumentMatchers.any(Employee.class))).thenReturn(employee);

        Employee created = service.create(employee);

        assertThat(created.getName()).isSameAs(employee.getName());
        verify(repository).save(employee);
    }

    @Test
    public void whenGivenId_shouldReturnEmployee_ifFound() {
        Employee employee = new Employee();
        employee.setId(88);

        when(repository.findById(employee.getId())).thenReturn(Optional.of(employee));

        Employee expected = service.getById(employee.getId());

        assertThat(expected).isSameAs(employee);
        verify(repository).findById(employee.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void should_throw_exception_when_employee_doesnt_exist() {
        Employee employee = new Employee();
        employee.setId(89);
        employee.setName("Mark");

        given(repository.findById(anyInt())).willReturn(Optional.empty());
        service.getById(employee.getId());
    }




    // №1
    @Test
    public void whenGivenName_shouldReturnList_ifFound() {
        Employee employee = new Employee();
        employee.setName("Mark");

        service.getName(employee.getName());

        verify(repository).getName(employee.getName());
    }

    // №2
    @Test
    public void whenAccess() {
        Employee employee = new Employee();
        employee.setId(7);

        service.isAccess(employee.getId());

        verify(repository).getIsAccess();
    }


    // №3
    @Test()
    public void whenGetHour() {
        Employee employee = new Employee();
        employee.setId(7);

        service.updateHour(employee.getId(), 23.0);

        verify(repository).updateHour(employee.getId(), 23.0);
    }
}
