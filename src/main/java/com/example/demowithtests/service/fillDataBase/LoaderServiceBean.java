package com.example.demowithtests.service.fillDataBase;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.EmployeeRepository;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@AllArgsConstructor
@Service
public class LoaderServiceBean implements LoaderService {

    private final EmployeeRepository employeeRepository;

    /**
     *
     */
    @Override
    public void generateData() {
        List<Employee> employees = createListEmployees();
        employeeRepository.saveAll(employees);
    }

    /**
     * @return
     */
    @Override
    public long count() {
        return employeeRepository.count();
    }

    public List<Employee> createListEmployees() {

        List<Employee> employees = new ArrayList<>();
        long seed = 1;

        Faker faker = new Faker(new Locale("en"), new Random(seed));
        for (int i = 0; i < 2_000; i++) {

            String name = faker.name().name();
            String country = faker.country().name();
            String email = faker.name().name();

            Set<Address> addresses = Set.copyOf(Arrays.asList(new Address(), new Address()));

            Employee employee = Employee
                    .builder()
                    .name(name)
                    .country(country)
                    .email(email.toLowerCase().replaceAll(" ", "") + "@mail.com")
                    .addresses(addresses)
                    .build();

            employees.add(employee);
        }

        return employees;
    }
}
