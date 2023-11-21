package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.emailSevice.EmailSenderService;
import com.example.demowithtests.util.annotations.entity.ActivateCustomAnnotations;
import com.example.demowithtests.util.annotations.entity.Name;
import com.example.demowithtests.util.annotations.entity.ToLowerCase;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import com.example.demowithtests.util.exception.ResourceWasDeletedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmailSenderService emailSenderService;

    @Override
    public int countEmployeesFromFrance() {
        return (int) employeeRepository.findAllFromFrance().stream()
                .filter(employee -> !employee.getIs_Deleted())
                .count();
    }

    @Override
    public List<Employee> findAllFromFrance() {
            return employeeRepository.findAllFromFrance();
    }

    @Override
    @ActivateCustomAnnotations({Name.class, ToLowerCase.class})
    // @Transactional(propagation = Propagation.MANDATORY)
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
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
        List<Employee> allEmployees = employeeRepository.findAll();

        return allEmployees.stream()
                .filter(employee -> !employee.getIs_Deleted())
                .collect(Collectors.toList());
    }

    @Override
    public Page<Employee> getAllWithPagination(Pageable pageable) {
        log.debug("getAllWithPagination() - start: pageable = {}", pageable);

        Page<Employee> page = employeeRepository.findAll(pageable);
        List<Employee> filteredList = page.getContent().stream()
                .filter(employee -> !employee.getIs_Deleted())
                .collect(Collectors.toList());

        Page<Employee> filteredPage = new PageImpl<>(filteredList, pageable, page.getTotalElements());

        log.debug("getAllWithPagination() - end: filteredPage = {}", filteredPage);
        return filteredPage;
    }

    @Override
    public Employee getById(Integer id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (Boolean.TRUE.equals(employee.getIs_Deleted())) {
            throw new EntityNotFoundException("Employee deleted with id = " + id);
        } else {
            return employee;
        }
    }


    public Employee updateById(Integer id, Employee employee) {
        return employeeRepository.findById(id)
                .map(entity -> {
                    if (Boolean.TRUE.equals(employee.getIs_Deleted())) {
                        throw new EntityNotFoundException("Employee with id = " + id + " is marked as deleted");
                    }
                    else {
                        entity.setName(employee.getName());
                        entity.setEmail(employee.getEmail());
                        entity.setCountry(employee.getCountry());
                        return employeeRepository.save(entity);
                    }
                })
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
    }

    @Override
    public Employee removeById(Integer id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(ResourceWasDeletedException::new);
        if (Boolean.TRUE.equals(employee.getIs_Deleted())) {
            throw new EntityNotFoundException("Employee was deleted with id = " + id);
        }else {
            employee.setIs_Deleted(Boolean.TRUE);
            employeeRepository.save(employee);
        }
        return employee;
    }

    @Override
    public void removeAllUsers() {
            employeeRepository.deleteAll();
    }


    @Override
    public Long countEmployees() {
        return employeeRepository.count();
    }

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
        List<String> countries = employeeList.stream()
                .map(country -> country.getCountry())
                .collect(Collectors.toList());
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
        return employeeList.stream()
                .map(Employee::getCountry)
                .filter(c -> c.startsWith("U"))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<String> findEmails() {
        var employeeList = employeeRepository.findAll();

        var emails = employeeList.stream()
                .map(Employee::getEmail)
                .collect(Collectors.toList());

        var opt = emails.stream()
                .filter(s -> s.endsWith(".com"))
                .findFirst()
                .orElse("error?");
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
                    String.format(
                            "Dear " + employee.getName() + "!\n" +
                                    "\n" +
                                    "The expiration date of your information is coming up soon. \n" +
                                    "Please. Don't delay in updating it. \n" +
                                    "\n" +
                                    "Best regards,\n" +
                                    "Ukrainian Info Service.")
            );
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


}
