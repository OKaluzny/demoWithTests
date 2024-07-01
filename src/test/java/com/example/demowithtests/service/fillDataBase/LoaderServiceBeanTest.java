package com.example.demowithtests.service.fillDataBase;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoaderServiceBeanTest {

    // In this test class, we are testing the LoaderServiceBean specifically focusing on
    // the method createListEmployees(). This method generates a list of Employee objects
    // with simulated data.

    @Test
    public void testCreateListEmployees() {
        //Given
        EmployeeRepository employeeRepositoryMock = Mockito.mock(EmployeeRepository.class);
        LoaderServiceBean loaderServiceBean = new LoaderServiceBean(employeeRepositoryMock);

        // When
        List<Employee> employees = loaderServiceBean.createListEmployees();

        // Then
        assertNotNull(employees);
        assertEquals(100000, employees.size());

        for (Employee employee : employees) {
            assertNotNull(employee.getName());
            assertNotNull(employee.getCountry());
            assertNotNull(employee.getEmail());

            Set<Address> addresses = employee.getAddresses();
            assertNotNull(addresses);
            assertEquals(2, addresses.size());

            for (Address address : addresses) {
                assertNotNull(address.getCountry());
                assertNotNull(address.getCity());
                assertNotNull(address.getStreet());
                assertNotNull(address.getAddressHasActive());
            }
        }
    }
}