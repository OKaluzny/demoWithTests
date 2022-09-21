//package com.example.demowithtests;
//
//import com.example.demowithtests.domain.Employee;
//import com.example.demowithtests.repository.Repository;
//import com.example.demowithtests.web.controllers.postController.Controller;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Ignore;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.notNullValue;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(Controller.class)
//public class ControllerTests {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper mapper;
//
//    @MockBean
//    Repository repository;
//
//    @Ignore
//    @Test
//    public void createEmployee_success() throws Exception {
//        Employee employee = Employee.builder()
//                .name("John")
//                .build();
//
//        Mockito.when(repository.save(employee)).thenReturn(employee);
//
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(this.mapper.writeValueAsString(employee));
//
//        mockMvc.perform(mockRequest)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$.firstName", is("John")));
//    }
//
//    @Ignore
//    @Test
//    public void getEmployeeById_success() throws Exception {
//
//        Employee employee = Employee.builder()
//                .name("Mark")
//                .country("France")
//                .build();
//
//        Mockito.when(repository.findById(employee.getId())).thenReturn(java.util.Optional.of(employee));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$.firstName", is("Mark")));
//    }
//
//    @Ignore
//    @Test
//    public void getAllEmployees_success() throws Exception {
//
//        Employee employee = Employee.builder()
//                .name("Mark")
//                .country("France")
//                .build();
//
//        List<Employee> records = new ArrayList<>(Arrays.asList(employee));
//
//        repository.save(employee);
//
//        Mockito.when(repository.findAll()).thenReturn(records);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/users")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[2].firstName", is("Mark")));
//    }
//
//}