package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.JpqlRepository;
import com.example.demowithtests.repository.Repository;
//import com.example.demowithtests.repository.SqlRepository;
//import com.example.demowithtests.service.ServiceBean;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import javax.persistence.EntityNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ServiceTests {
//
//    @Mock
//    private Repository repository;
//    @Mock
//    private SqlRepository sqlRepository;
//    @Mock
//    private JpqlRepository jpqlRepository;
//    @InjectMocks
//    private ServiceBean service;
//
//
//    @Test
//    @Ignore
//    public void whenSaveEmployee_shouldReturnEmployee() {
//        Employee employee = new Employee();
//        employee.setName("Mark");
//        employee.setIsAdult(true);
//
//        when(repository.save(ArgumentMatchers.any(Employee.class))).thenReturn(employee);
//        Employee created = service.create(employee);
//
//        assertThat(created.getName()).isSameAs(employee.getName());
//        //verify(repository).save(employee);
//    }
//
//    @Test
//    public void whenGivenId_shouldReturnEmployee_ifFound() {
//        Employee employee = new Employee();
//        employee.setId(88);
//
//        when(repository.findById(employee.getId())).thenReturn(Optional.of(employee));
//
//        Employee expected = service.getById(employee.getId());
//
//        assertThat(expected).isSameAs(employee);
//        verify(repository).findById(employee.getId());
//    }
//
//    @Test(expected = EntityNotFoundException.class)
//    public void should_throw_exception_when_employee_doesnt_exist() {
//        Employee testEmployee = Employee.builder().id(89).name("Max").build();
//
//        given(repository.findById(anyInt())).willReturn(Optional.empty());
//        service.getById(testEmployee.getId());
//    }
//
//    @Test
//    public void whenGivenName_shouldTestEmployeeList(){
//        Employee testEmployee = Employee.builder().name("Max").build();
//        List<Employee> testEmployeeList = new ArrayList<>();
//        testEmployeeList.add(testEmployee);
//
//        when(sqlRepository.findUserByName(testEmployee.getName())).thenReturn(testEmployeeList);
//        List<Employee> expectedTestEmployeeList = service.findUserByName(testEmployee.getName());
//
//        assertThat(expectedTestEmployeeList.get(0).getName()).isEqualTo("Max");
//    }
//
//    @Test
//    public void whenGivenEmail_shouldTestEmployeeList(){
//        Employee testEmployee = Employee.builder().email("max@gmail.com").build();
//        List<Employee> testEmployeeList = new ArrayList<>();
//        testEmployeeList.add(testEmployee);
//
//        when(sqlRepository.findEmployeeByEmail(testEmployee.getEmail())).thenReturn(testEmployeeList);
//        List<Employee> expectedTestEmployeeList = service.findEmployeeByEmail(testEmployee.getEmail());
//
//        assertThat(expectedTestEmployeeList.get(0).getEmail()).isEqualTo("max@gmail.com");
//    }
//
//    @Test
//    public void whenGivenIsDeletedField_shouldTestEmployeeList(){
//        Employee testEmployee = Employee.builder().isDeleted(true).build();
//        List<Employee> testEmployeeList = new ArrayList<>();
//        testEmployeeList.add(testEmployee);
//
//        when(sqlRepository.findAllByIsDeleted(testEmployee.getIsDeleted())).thenReturn(testEmployeeList);
//        List<Employee> expectedTestEmployeeList = service.findAllByIsDeleted(testEmployee.getIsDeleted());
//
//        assertThat(expectedTestEmployeeList.get(0).getIsDeleted()).isEqualTo(true);
//    }
//
//    @Test
//    public void whenGivenIsAdultField_shouldTestEmployeeList(){
//        Employee testEmployee = Employee.builder().isAdult(true).build();
//        List<Employee> testEmployeeList = new ArrayList<>();
//        testEmployeeList.add(testEmployee);
//
//        when(jpqlRepository.findAdultUser(testEmployee.getIsAdult())).thenReturn(testEmployeeList);
//        List<Employee> expectedTestEmployeeList = service.findAdultUser(testEmployee.getIsAdult());
//
//        assertThat(expectedTestEmployeeList.get(0).getIsAdult()).isEqualTo(true);
//    }
//
//    @Test
//    public void whenGivenCountry_shouldTestEmployeeList(){
//        Employee testEmployee = Employee.builder().country("Ukraine").build();
//        List<Employee> testEmployeeList = new ArrayList<>();
//        testEmployeeList.add(testEmployee);
//
//        when(jpqlRepository.findEmployeeByCountry(testEmployee.getCountry())).thenReturn(testEmployeeList);
//        List<Employee> expectedTestEmployeeList = service.findEmployeeByCountry(testEmployee.getCountry());
//
//        assertThat(expectedTestEmployeeList.get(0).getCountry()).isEqualTo("Ukraine");
//    }
//
//
//
//}
