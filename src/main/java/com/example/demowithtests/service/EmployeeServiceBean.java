package com.example.demowithtests.service;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Document;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.DocDto;
import com.example.demowithtests.dto.DocumentDto;
import com.example.demowithtests.dto.EmployeeAndAgeDto;
import com.example.demowithtests.dto.EmployeeAndDocumentDto;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.emailSevice.EmailSenderService;
import com.example.demowithtests.service.history.HistoryService;
import com.example.demowithtests.util.annotations.entity.ActivateCustomAnnotations;
import com.example.demowithtests.util.annotations.entity.Name;
import com.example.demowithtests.util.annotations.entity.ToLowerCase;
import com.example.demowithtests.util.exception.EmailException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import com.example.demowithtests.util.exception.ResourceWasDeletedException;
import com.example.demowithtests.util.exception.SoftDeleteException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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

    public Employee saveEmployeeWithAge(EmployeeAndAgeDto employee) {
        Employee employee1 = Employee.builder()
                .name(employee.getName())
                .age(employee.getAge())
                .country(employee.getCountry())
                .email(employee.getEmail())
                .build();
        try {
            return employeeRepository.save(employee1);
        }catch (DataIntegrityViolationException e){
            throw  e;
        }


    }
    @Override
    public List<Employee> findAllEmployee() {
        return employeeRepository.findAll(); }
    @Override
    public List<Employee> findAllEmployeeWithoutGraph(){
        return employeeRepository.findAll();

    }


    /**
     * Creates a new employee.
     *
     * @param employee the Employee object to be created
     * @return the newly created Employee object
     */
    @Override
    @ActivateCustomAnnotations({Name.class, ToLowerCase.class, Email.class})
    public Employee create( Employee employee) {
        if(employeeRepository.existsByEmail(employee.getEmail())) {
            throw new EmailException();
        }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAllActive() {
        return employeeRepository.findAllActive();
    }

    @Override
    public Optional<Employee> findActiveById(Integer id) {
        return employeeRepository.findActiveById(id);
    }

    @Override
    public void softDelete(Integer id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (employeeOptional.isPresent()) {
            if (employeeOptional.get().getIsDeleted()) {
                throw  new SoftDeleteException();
            }
            Employee employee = employeeOptional.get();
            employee.setIsDeleted(true);
            employeeRepository.save(employee);
        }
    }

    /**
     * @param employee
     * @return
     */
    @Override
    public void createAndSave(Employee employee) {
        employeeRepository.saveEmployee(employee.getName(),
                                        employee.getEmail(),
                                        employee.getCountry(),
                                        String.valueOf(employee.getGender()));
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
    @Override
    public EmployeeAndDocumentDto getEmployeeWithDocuments(Integer id){
        Optional<Employee> employee = employeeRepository.findById(id);
        DocDto doc = DocDto.builder()
                .id(employee.get().getDocument().getId())
                .number(employee.get().getDocument().getNumber())
                .uuid(employee.get().getDocument().getUuid())
                .expireDate(employee.get().getDocument().getExpireDate())
                .isHandled(employee.get().getDocument().getIsHandled())
                .build();
        EmployeeAndDocumentDto dto = EmployeeAndDocumentDto.builder()
                .id(employee.get().getId())
                .name(employee.get().getName())
                .country(employee.get().getCountry())
                .email(employee.get().getEmail())
                .documentNumber(employee.get().getDocument().getNumber())
                .document(doc)
                .build();

        return dto;


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
            entity.setName(employee.getName());
            entity.setEmail(employee.getEmail());
            entity.setCountry(employee.getCountry());
            return employeeRepository.save(entity);
        }).orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
    }

    @Override
    public void removeById(Integer id) {
        //repository.deleteById(id);
        var employee = employeeRepository.findById(id)
                // .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
                .orElseThrow(ResourceWasDeletedException::new);
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
        return employeeList.stream().map(Employee::getCountry)
                .filter(c -> c.startsWith("U"))
                .sorted(Comparator.naturalOrder()).collect(Collectors.toList());
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
        var ukrainians = employeeRepository.findAllUkrainian()
                .orElseThrow(() -> new EntityNotFoundException("Employees from Ukraine not found!"));
        var emails = new HashSet<String>();
        ukrainians.forEach(employee -> {
            emailSenderService.sendEmail(
                    /*employee.getEmail(),*/
                    "kaluzny.oleg@gmail.com", //для тесту
                    "Need to update your information",
                    String.format("Dear " + employee.getName() + "!\n" + "\n" + "The expiration date of your" +
                            " information is coming up soon. \n" + "Please. Don't delay in updating it. \n" + "\n" +
                            "Best regards,\n" + "Ukrainian Info Service."));
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
     * @param id       the ID of the employee
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
    @Override
    public Employee addAddress(Integer id, Address address) {
        return employeeRepository.findById(id)
               // .filter(e ->e.getIsDeleted().equals(false))
                .map(entity -> {
                    entity.getAddresses().add(address);
                   return employeeRepository.save(entity);

                })
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
    }
    @Override
    public Employee deaktivateAddress(Integer addressId, Integer employeeId) {
        return employeeRepository.findById(employeeId)
                .map(entity -> {
                    entity.getAddresses().stream()
                            .filter(address -> address.getId().equals(addressId))
                            .findFirst()
                            .ifPresent(address -> {
                                address.setAddressHasActive(false);
                            });
                    return employeeRepository.save(entity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + employeeId));
    }
    @Override
    public List<Employee> getAllUsersWithMoreThenOneAddress(){
        return employeeRepository.findAllWithMoreThanOneAddress();
    }
    @Override
    public  List<Employee> getAllUsersWithOneAddress(){
        return employeeRepository.findAllWithOneAddress();
    }
    @Override
    public List<Employee> getAllUsersWithNoAddress(){
        return employeeRepository.findEmployeeWithoutAddresses();
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
}
