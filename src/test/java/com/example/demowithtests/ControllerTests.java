package com.example.demowithtests;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Document;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.DocDto;
import com.example.demowithtests.dto.EmployeeAndDocumentDto;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.service.EmployeeServiceEM;
import com.example.demowithtests.service.document.DocumentService;
import com.example.demowithtests.util.mappers.DocumentMapper;
import com.example.demowithtests.util.mappers.EmployeeMapper;
import com.example.demowithtests.web.EmployeeController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = EmployeeController.class)
@DisplayName("Employee Controller Tests")
@Disabled
public class ControllerTests {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    EmployeeService service;


    @MockBean
    EmployeeServiceEM serviceEM;

    @MockBean
    EmployeeMapper employeeMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentMapper documentMapper;
    @MockBean
    private DocumentService documentService;

    @Test
    @DisplayName("GET API -> /api/users/graph")
    @WithMockUser(roles = "USER")
    public void getAllEmployeesGraphTest() throws Exception {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("John Doe");

        Employee employee1 = new Employee();
        employee1.setId(2);
        employee1.setName("Max Doe");
        List<Employee> employees = List.of( employee1,employee);
        when(service.findAllEmployeeWithoutGraph()).thenReturn(employees);

        mockMvc.perform(get("/api/users/graph"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Max Doe"))
                .andExpect(jsonPath("$[1].name").value("John Doe"));

    }

    @Test
    @DisplayName("DELETE API -> /api/users/soft/{id}")
    @WithMockUser(roles = "USER")
    public void softDeleteEmployeeTest() throws Exception {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("John Doe");
        employee.setIsDeleted(true);
      when(service.getById(1)).thenReturn(employee);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/users/soft/{id}", 1)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.isDeleted").value(true));
    }


    @Test
    @DisplayName("GET API -> /api/users/active")
    @WithMockUser(roles = "USER")
    public void getAllActiveEmployeesTest() throws Exception {
        Employee employee1 = new Employee();
        employee1.setId(1);
        employee1.setName("John Doe");
        employee1.setIsDeleted(false);
        Employee employee2 = new Employee();
        employee2.setId(2);
        employee2.setName("Jane Smith");
        employee2.setIsDeleted(false);

        when(service.findAllActive()).thenReturn(Arrays.asList(employee1, employee2));

        mockMvc.perform(get("/api/users/active")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
    }


    @Test
    @DisplayName("POST API -> /api/users/address/deactive/{addressId}/{userId}")
    @WithMockUser(roles = "USER")
    public void softDeleteAddressTest() throws Exception {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Mike");
        employee.setEmail("mail@mail.com");
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Street");
        address.setCity("City");
        address.setCountry("Country");
        address.setAddressHasActive(false);
        employee.getAddresses().add(address);
        when(service.deaktivateAddress(1, 1)).thenReturn(employee);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/users/address/deactive/{addressId}/{userId}", 1, 1)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Mike")))
                .andExpect(jsonPath("$.email", is("mail@mail.com")))
                .andExpect(jsonPath("$.addresses[0].id", is(1)))
                .andExpect(jsonPath("$.addresses[0].street", is("Street")))
                .andExpect(jsonPath("$.addresses[0].city", is("City")))
                .andExpect(jsonPath("$.addresses[0].country", is("Country")))
                .andExpect(jsonPath("$.addresses[0].addressHasActive", is(false)));

        verify(service, times(1)).deaktivateAddress(1, 1);

    }


    @Test
    @DisplayName("GET API -> /api/users/more/one/address")
    @WithMockUser(roles = "USER")
    public void getAllUsersWithMoreThenOneAddressTest() throws Exception {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Street");
        address.setCity("City");
        address.setCountry("Country");

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Mike");
        employee.setEmail("mail@mail.com");
        employee.getAddresses().add(address);
        List<Employee> response = List.of(employee);
        when(service.getAllUsersWithMoreThenOneAddress()).thenReturn(response);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/users/more/one/address")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Mike")))
                .andExpect(jsonPath("$[0].email", is("mail@mail.com")))
                .andExpect(jsonPath("$[0].addresses[0].id", is(1)));

    }

    @Test
    @DisplayName("GET API -> /api/users/no/address")
    @WithMockUser(roles = "USER")
    public void getAllUsersWithNoAddressTest() throws Exception {

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Mike");
        employee.setEmail("mail@mail.com");

        List<Employee> response = List.of(employee);
        when(service.getAllUsersWithNoAddress()).thenReturn(response);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/users/no/address")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Mike")))
                .andExpect(jsonPath("$[0].email", is("mail@mail.com")));
    }

    @Test
    @DisplayName("GET API -> /api/user/with/document/{id}")
    @WithMockUser(roles = "USER")
    public void getEmployeeWithDocumentTest() throws Exception {
        DocDto doc = new DocDto();
        doc.setId(1);
        doc.setUuid("url");
        doc.setId(1);

        EmployeeAndDocumentDto response = new EmployeeAndDocumentDto();
        response.setId(1);
        response.setName("Mike");
        response.setEmail("mail@mail.com");
        response.setDocument(doc);

        when(service.getEmployeeWithDocuments(1)).thenReturn(response);


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/user/with/document/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON);


        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name").value("Mike"))
                .andExpect(jsonPath("$.email").value("mail@mail.com"))
                .andExpect(jsonPath("$.document.id", is(1)))
                .andExpect(jsonPath("$.document.uuid").value("url"));

        verify(service).getEmployeeWithDocuments(1);


    }

    @Test
    @DisplayName("POST API -> /api/users")
    @WithMockUser(roles = "ADMIN")
    public void createPassTest() throws Exception {


        EmployeeDto response = new EmployeeDto(
                1, "Mike", "England", "mail@mail.com",
                null, null, null);

        var employee = Employee.builder()
                .id(1)
                .name("Mike")
                .email("mail@mail.com").build();

        when(employeeMapper.toEmployee(any(EmployeeDto.class))).thenReturn(employee);
        when(employeeMapper.toEmployeeDto(any(Employee.class))).thenReturn(response);
        when(service.create(any(Employee.class))).thenReturn(employee);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/users")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name").value("Mike"));

        verify(service).create(any());
    }

    @Test
    @DisplayName("POST API -> /api/users/jpa")
    @WithMockUser(roles = "USER")
    public void testSaveWithJpa() throws Exception {

        var employeeToBeReturn = Employee.builder()
                .id(1)
                .name("Mark")
                .country("France").build();

        doReturn(employeeToBeReturn).when(serviceEM).createWithJpa(any());
        when(this.serviceEM.createWithJpa(any(Employee.class))).thenReturn(employeeToBeReturn);
        // Execute the POST request
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/api/users/jpa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employeeToBeReturn))
                .with(csrf());
        mockMvc
                .perform(builder)
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        verify(this.serviceEM, times(1)).createWithJpa(any(Employee.class));
        verifyNoMoreInteractions(this.serviceEM);
    }

    @Test
    @DisplayName("GET API -> /api/users/{id}")
    @WithMockUser(roles = "USER")
    public void getPassByIdTest() throws Exception {

        var response = new EmployeeReadDto();
        response.id = 1;
        response.name = "Mike";

        var employee = Employee.builder()
                .id(1)
                .name("Mike")
                .build();

        when(employeeMapper.toEmployeeReadDto(any(Employee.class))).thenReturn(response);
        when(service.getById(1)).thenReturn(employee);

        MockHttpServletRequestBuilder mockRequest = get("/api/users/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name", is("Mike")));

        verify(service).getById(1);
    }

    @Test
    @DisplayName("PUT API -> /api/users/{id}")
    @WithMockUser(roles = "ADMIN")
    public void updatePassByIdTest() throws Exception {
        var response = new EmployeeReadDto();
        response.id = 1;
        var employee = Employee.builder().id(1).build();

        when(employeeMapper.toEmployee(any(EmployeeDto.class))).thenReturn(employee);
        when(service.updateById(eq(1), any(Employee.class))).thenReturn(employee);
        when(employeeMapper.toEmployeeReadDto(any(Employee.class))).thenReturn(response);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/users/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(service).updateById(eq(1), any(Employee.class));
    }

    @Test
    @DisplayName("DELETE API -> /api/users/{id}")
    @WithMockUser(roles = "ADMIN")
    public void deletePassTest() throws Exception {

        doNothing().when(service).removeById(1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/api/users/1")
                .with(csrf());

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());

        verify(service).removeById(1);
    }

//    @Test
//    @DisplayName("GET API -> /api/users/pages")
//    @WithMockUser(roles = "ADMIN")
//    public void getUsersPageTest() throws Exception {
//
//        var employee = Employee.builder().id(1).name("John").country("US").build();
//        var employeeTwo = Employee.builder().id(2).name("Jane").country("UK").build();
//        var employeeThree = Employee.builder().id(3).name("Bob").country("US").build();
//
//        List<Employee> list = Arrays.asList(employee, employeeTwo, employeeThree);
//        Page<Employee> employeesPage = new PageImpl<>(list);
//        Pageable pageable = PageRequest.of(0, 3);
//
//        EmployeeReadDto dto = new EmployeeReadDto();
//        EmployeeReadDto dtoTwo = new EmployeeReadDto();
//        EmployeeReadDto dtoThree = new EmployeeReadDto();
//
//        when(service.getAllWithPagination(pageable)).thenReturn(employeesPage);
//        when(employeeMapper.toEmployeeReadDto(employee)).thenReturn(dto);
//        when(employeeMapper.toEmployeeReadDto(employeeTwo)).thenReturn(dtoTwo);
//        when(employeeMapper.toEmployeeReadDto(employeeThree)).thenReturn(dtoThree);
//
//        MvcResult result = mockMvc.perform(get("/api/users/pages")
//                        .param("page", "0")
//                        .param("size", "3"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        verify(service).getAllWithPagination(eq(pageable));
//        verify(employeeMapper, times(1)).toEmployeeReadDto(employee);
//        verify(employeeMapper, times(1)).toEmployeeReadDto(employeeTwo);
//        verify(employeeMapper, times(1)).toEmployeeReadDto(employeeThree);
//
//        String contentType = result.getResponse().getContentType();
//        assertNotNull(contentType);
//        assertTrue(contentType.contains(MediaType.APPLICATION_JSON_VALUE));
//        String responseContent = result.getResponse().getContentAsString();
//        assertNotNull(responseContent);
//    }

}