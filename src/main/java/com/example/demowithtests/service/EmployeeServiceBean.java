package com.example.demowithtests.service;

import com.example.demowithtests.domain.Document;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.emailSevice.EmailSenderService;
import com.example.demowithtests.service.history.HistoryService;
import com.example.demowithtests.service.kafka.EmployeeEventPublisher;
import com.example.demowithtests.util.annotations.entity.ActivateCustomAnnotations;
import com.example.demowithtests.util.annotations.entity.Name;
import com.example.demowithtests.util.annotations.entity.ToLowerCase;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import com.example.demowithtests.util.exception.ResourceWasDeletedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class implements the EmployeeService interface and provides the business logic for managing employees.
 * It utilizes the EmployeeRepository, EmailSenderService, and HistoryService dependencies.
 */
@Slf4j
@AllArgsConstructor
@Service
public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmailSenderService emailSenderService;
    private final HistoryService historyService;
    private final EmployeeEventPublisher employeeEventProducer;


    /**
     * Creates a new employee.
     *
     * @param employee the Employee object to be created
     * @return the newly created Employee object
     */
    @Override
    @ActivateCustomAnnotations({Name.class, ToLowerCase.class})
    // @Transactional(propagation = Propagation.MANDATORY)
    public Employee create(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);

        // Send Kafka event for employee creation
        try {
            employeeEventProducer.sendEmployeeCreatedEvent(savedEmployee);
        } catch (Exception e) {
            log.error("Failed to send employee created event for employee ID: {}", savedEmployee.getId(), e);
        }

        return savedEmployee;
        //return employeeRepository.saveAndFlush(employee);
    }

    /**
     * @param employee
     * @return
     */
    @Override
    public void createAndSave(Employee employee) {
        employeeRepository.saveEmployee(employee.getName(), employee.getEmail(), employee.getCountry(), String.valueOf(employee.getGender()));
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Page<Employee> getAllWithPagination(Pageable pageable) {
        log.debug("getAllWithPagination() - start: pageable = {}", pageable);
        Page<Employee> list = employeeRepository.findAll(pageable);
        log.debug("getAllWithPagination() - end: list = {}", list);
        return list;
    }

    /**
     * Retrieves an employee by their ID.
     *
     * @param id the ID of the employee to retrieve
     * @return the employee with the given ID, if found
     * @throws ResourceNotFoundException if no employee is found with the given ID
     */
    @Override
    public Employee getById(Integer id) {
        var employee = employeeRepository.findById(id)
                // .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
                .orElseThrow(ResourceNotFoundException::new);
        /* if (employee.getIsDeleted()) {
            throw new EntityNotFoundException("Employee was deleted with id = " + id);
        }*/
        return employee;
    }

    /**
     * Updates an employee by their id.
     *
     * @param id       the id of the employee to be updated
     * @param employee the updated employee object
     * @return the updated employee
     * @throws EntityNotFoundException if the employee with the given id is not found
     */
    @Override
    public Employee updateById(Integer id, Employee employee) {
        return employeeRepository.findById(id).map(entity -> {
            // Create a copy of the previous employee data for Kafka event
            Employee previousEmployee = Employee.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .email(entity.getEmail())
                    .country(entity.getCountry())
                    .gender(entity.getGender())
                    .build();

            // Update the entity
            entity.setName(employee.getName());
            entity.setEmail(employee.getEmail());
            entity.setCountry(employee.getCountry());
            Employee updatedEmployee = employeeRepository.save(entity);

            // Send Kafka event for employee update
            try {
                employeeEventProducer.sendEmployeeUpdatedEvent(previousEmployee, updatedEmployee);
            } catch (Exception e) {
                log.error("Failed to send employee updated event for employee ID: {}", updatedEmployee.getId(), e);
            }

            return updatedEmployee;
        }).orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
    }

    @Override
    public void removeById(Integer id) {
        //repository.deleteById(id);
        var employee = employeeRepository.findById(id)
                // .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
                .orElseThrow(ResourceWasDeletedException::new);

        // Send Kafka event for employee deletion before deleting
        try {
            employeeEventProducer.sendEmployeeDeletedEvent(employee);
        } catch (Exception e) {
            log.error("Failed to send employee deleted event for employee ID: {}", employee.getId(), e);
        }

        //employee.setIsDeleted(true);
        employeeRepository.delete(employee);
        //repository.save(employee);
    }

   /* @Override
    public void removeById(Integer id) {
        employeeRepository.findById(id)
                .filter(this::IsEmployeePresent)
                .map(employee -> {
                    employee.setIsDeleted(Boolean.TRUE);
                    return employeeRepository.save(employee);
                })
                .orElseThrow(ResourceWasDeletedException::new);
    }*/

    @Override
    public void removeAll() {
        employeeRepository.deleteAll();
    }

    /*private boolean IsEmployeePresent(Employee employee) {
        Boolean isDeleted = employee.getIsDeleted();
        if (isDeleted != null && isDeleted.equals(Boolean.FALSE)) return true;
        else return false;
    }

    @Override
    public void removeAll() {
        List<Employee> list =
                employeeRepository.findAll().stream()
                        .filter(this::IsEmployeePresent)
                        .peek(emp -> emp.setIsDeleted(Boolean.TRUE))
                        .toList();

        employeeRepository.saveAll(list);
    }*/

    /*@Override
    public Page<Employee> findByCountryContaining(String country, Pageable pageable) {
        return employeeRepository.findByCountryContaining(country, pageable);
    }*/

    @Override
    public Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder) {
        // create Pageable object using the page, size and sort details
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        // fetch the page object by additionally passing pageable with the filters
        return employeeRepository.findByCountryContaining(country, pageable);
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

    @Override
    public List<String> getAllEmployeeCountry() {
        log.info("getAllEmployeeCountry() - start:");
        List<Employee> employeeList = employeeRepository.findAll();
        List<String> countries = employeeList.stream().map(country -> country.getCountry()).collect(Collectors.toList());
        /*List<String> countries = employeeList.stream()
                .map(Employee::getCountry)
                //.sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());*/

        log.info("getAllEmployeeCountry() - end: countries = {}", countries);
        return countries;
    }

    @Override
    public List<String> getSortCountry() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream().map(Employee::getCountry).filter(c -> c.startsWith("U")).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    @Override
    public Optional<String> findEmails() {
        var employeeList = employeeRepository.findAll();

        var emails = employeeList.stream().map(Employee::getEmail).collect(Collectors.toList());

        var opt = emails.stream().filter(s -> s.endsWith(".com")).findFirst().orElse("error?");
        return Optional.ofNullable(opt);
    }

    @Override
    public List<Employee> filterByCountry(String country) {
        return employeeRepository.findEmployeesByCountry(country);
    }

    @Override
    public Set<String> sendEmailsAllUkrainian() {
        var ukrainians = employeeRepository.findAllUkrainian().orElseThrow(() -> new EntityNotFoundException("Employees from Ukraine not found!"));
        var emails = new HashSet<String>();
        ukrainians.forEach(employee -> {
            emailSenderService.sendEmail(
                    /*employee.getEmail(),*/
                    "kaluzny.oleg@gmail.com", //для тесту
                    "Need to update your information", String.format("Dear " + employee.getName() + "!\n" + "\n" + "The expiration date of your information is coming up soon. \n" + "Please. Don't delay in updating it. \n" + "\n" + "Best regards,\n" + "Ukrainian Info Service."));
            emails.add(employee.getEmail());
        });

        return emails;
    }

    /**
     * @param name
     * @return
     */
    @Override
    public List<Employee> findByNameContaining(String name) {
        return employeeRepository.findByNameContaining(name);
    }

    /**
     * @param name
     * @param id
     * @return
     */
    @Override
    public void updateEmployeeByName(String name, Integer id) {
        /*var employee = employeeRepository.findById(id)
                .map(entity -> {
                    entity.setName(name);
                    return employeeRepository.save(entity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
        return employee;*/

        employeeRepository.updateEmployeeByName(name, id);
    }

    /**
     * Checks if there are any duplicate emails in the employee repository.
     *
     * @param email    the email address to check for duplicates
     * @param pageable the pageable configuration for the query
     * @return a page of Employee objects matching the given email, with pagination applied
     */
    @Override
    public Page<Employee> checkDuplicateEmails(String email, Pageable pageable) {
        return employeeRepository.findEmployeesByEmail(email, pageable);
    }

    /**
     * Sets the document for the employee with the specified ID.
     *
     * @param id the ID of the employee
     * @param document the document to be assigned to the employee
     * @return the updated employee with the assigned document
     * @throws EntityNotFoundException if no employee is found with the specified ID
     */
    @Override
    public Employee setDocument(Integer id, Document document) {
        return employeeRepository.findById(id)
                .map(entity -> {
            entity.setDocument(document);
                    historyService.create("The document was assigned to the person with id: " + id,
                            entity.getDocument());
                    return employeeRepository.save(entity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
    }

    /**
     * Removes the document of an employee with the specified ID.
     *
     * @param id the ID of the employee
     * @return the updated Employee object with the document removed
     * @throws EntityNotFoundException if no employee is found with the specified ID
     */
    @Override
    public Employee removeDocument(Integer id) {
        return employeeRepository.findById(id)
                .map(entity -> {
                    historyService.create("The document was removed from the person with id: " + id,
                            entity.getDocument());
                    entity.setDocument(null);
            return employeeRepository.save(entity);
        }).orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
    }

    /**
     * Updates an employee's name, email, and country in the database.
     *
     * @param name    The new name of the employee.
     * @param email   The new email of the employee.
     * @param country The new country of the employee.
     * @param id      The id of the employee to update.
     * @return The number of rows affected by the update operation.
     */
    @Override
    public Integer updateEmployee(String name, String email, String country, Integer id) {
        return employeeRepository.updateEmployee(name, email, country, id);
    }

    @Override
    public Employee findEmployeeByEmail(String email) {
        return employeeRepository.findEmployeesByEmail(email);
    }
}
