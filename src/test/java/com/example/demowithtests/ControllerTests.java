package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.EmployeeServiceBean;
import com.example.demowithtests.web.EmployeeController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EmployeeController.class)
@DisplayName("Employee Controller Tests")
public class ControllerTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    EmployeeRepository employeeRepository;

    @MockBean
    EmployeeServiceBean employeeServiceBean;

    @MockBean
    EmployeeController employeeController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        given(this.employeeRepository.findById(1))
                .willReturn(Optional.of(Employee.builder().name("test").build()));
        given(this.employeeRepository.findById(2))
                .willReturn(Optional.empty());
        given(this.employeeRepository.save(any(Employee.class)))
                .willReturn(Employee.builder().name("test").build());
        given(this.employeeRepository.findByName(any()))
                .willReturn(Employee.builder().name("test").build());
        doNothing().when(this.employeeRepository).delete(any(Employee.class));
    }

    @Test
    @DisplayName("POST /api/users")
    public void createEmployee_success() throws Exception {
        Employee employee = Employee.builder()
                .name("John")
                .build();

        Mockito.when(employeeServiceBean.create(employee)).thenReturn(employee);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                //.accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(employee));

        this.mockMvc
                .perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    @DisplayName("POST /api/users")
    public void testSave() throws Exception {
        // Set up our mocked service
        Employee employeeToBeReturn = Employee.builder().name("Mark").country("France").build();
        doReturn(employeeToBeReturn).when(employeeRepository).save(any());
        // Execute the POST request
        this.mockMvc
                .perform(post("/api/usersS")
                        .content(this.mapper
                                .writeValueAsBytes(Employee
                                        .builder()
                                        .name("Driver")
                                        .build()))
                        .contentType(MediaType.APPLICATION_JSON))
                // Validate the response code and content type
                .andExpect(status().isCreated());

        // verify(this.employeeRepository, times(1)).save(any(Employee.class));
        verifyNoMoreInteractions(this.employeeRepository);

    }

    @Test
    public void getEmployeeById_success() throws Exception {

        Employee employee = Employee.builder().name("Mark").country("France").build();

        Mockito.when(employeeRepository
                .findById(employee.getId())).thenReturn(java.util.Optional.of(employee));

        mockMvc
                .perform(get("/api/users/1", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName").value("Mark"));

        verify(this.employeeRepository, times(1)).findById(any(Integer.class));
        verifyNoMoreInteractions(this.employeeRepository);
    }

    @Test
    public void getAllEmployees_success() throws Exception {

        Employee employee = Employee.builder()
                .name("Mark")
                .country("France")
                .build();

        List<Employee> records = new ArrayList<>(Arrays.asList(employee));

        employeeRepository.save(employee);

        Mockito.when(employeeRepository.findAll()).thenReturn(records);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[2].firstName", is("Mark")));
    }

}