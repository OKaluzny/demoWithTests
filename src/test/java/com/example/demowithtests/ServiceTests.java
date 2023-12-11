package com.example.demowithtests;

import com.example.demowithtests.domain.Document;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.EmployeeServiceBean;
import com.example.demowithtests.service.history.HistoryService;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Employee Service Tests")
public class ServiceTests {

    @Mock
    private HistoryService historyService;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceBean service;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder().id(1).name("Mark").country("Ukraine").email("test@mail.com").gender(Gender.M).build();
    }

    @Test
    @DisplayName("Save employee test")
    public void whenSaveEmployee_shouldReturnEmployee() {

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
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

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        service.removeById(employee.getId());
        verify(employeeRepository).delete(employee);
    }

    /**
     * Tests whether checkDuplicateEmails() returns correct paged employees when duplicate emails exist.
     */
    @Test
    public void testCheckDuplicateEmailsWhenDuplicateEmailsExist() {
        // Arrange
        EmployeeRepository mockRepository = mock(EmployeeRepository.class);
        EmployeeServiceBean serviceBean = new EmployeeServiceBean(
                mockRepository,
                null,
                null
        );
        String testEmailAddress = "test@email.com";
        Employee mockEmployeeOne = new Employee();
        mockEmployeeOne.setEmail(testEmailAddress);
        Employee mockEmployeeTwo = new Employee();
        mockEmployeeTwo.setEmail(testEmailAddress);
        List<Employee> mockEmployees = List.of(mockEmployeeOne, mockEmployeeTwo);
        Page<Employee> mockPage = new PageImpl<>(mockEmployees);
        when(mockRepository.findEmployeesByEmail(testEmailAddress, PageRequest.of(0, 10))).thenReturn(mockPage);

        // Act
        Page<Employee> result = serviceBean.checkDuplicateEmails(testEmailAddress, PageRequest.of(0, 10));

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        result.get().forEach(employee -> assertEquals(testEmailAddress, employee.getEmail()));
    }

    /**
     * Tests checkDuplicateEmails() method when no duplicate emails exist.
     */
    @Test
    public void testCheckDuplicateEmailsWhenNoDuplicateEmailsExist() {
        // Arrange
        EmployeeRepository mockRepository = mock(EmployeeRepository.class);
        EmployeeServiceBean serviceBean = new EmployeeServiceBean(
                mockRepository,
                null,
                null
        );
        String testEmailAddress = "test@email.com";
        Page<Employee> mockPage = Page.empty();
        when(mockRepository.findEmployeesByEmail(testEmailAddress, PageRequest.of(0, 10))).thenReturn(mockPage);

        // Act
        Page<Employee> result = serviceBean.checkDuplicateEmails(testEmailAddress, PageRequest.of(0, 10));

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void setDocumentReturnsUpdatedEmployee() {
        // Arrange
        Integer id = 1;
        Document document = new Document();
        document.setNumber("123");
        Employee employee = new Employee();
        Employee employeeWithDocument = new Employee();
        employeeWithDocument.setDocument(document);

        given(employeeRepository.findById(id)).willReturn(Optional.of(employee));
        given(employeeRepository.save(employee)).willReturn(employeeWithDocument);
        // Act
        Employee actualEmployee = service.setDocument(id, document);
        // Assert
        assertEquals(employeeWithDocument, actualEmployee, "The actual employee does not match the expected employee.");
        verify(historyService).create("The document was assigned to the person with id: " + id, document);
    }

    @Test
    void setDocumentThrowsExceptionWhenEmployeeNotFound() {
        // Arrange
        Document document = new Document();
        document.setNumber("123");
        Integer id = 1;
        given(employeeRepository.findById(id)).willReturn(Optional.empty());
        // Assert
        assertThrows(jakarta.persistence.EntityNotFoundException.class, () -> service.setDocument(id, document), "setDocument did not throw an exception as expected when attempting to assign document to non-existent employee.");
    }

    @Test
    public void testFindByNameContaining() {
        // arrange
        List<Employee> employeeList = new ArrayList<>();
        Employee employee1 = new Employee();
        employee1.setName("John");
        Employee employee2 = new Employee();
        employee2.setName("Smith");
        employeeList.add(employee1);
        employeeList.add(employee2);
        when(employeeRepository.findByNameContaining(anyString())).thenReturn(employeeList);
        // act
        List<Employee> result = service.findByNameContaining("John");
        // assert
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("John");
        assertThat(result.get(1).getName()).isEqualTo("Smith");
    }
}

