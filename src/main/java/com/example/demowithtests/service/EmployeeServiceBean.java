package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.util.annotations.entity.ActivateCustomAnnotations;
import com.example.demowithtests.util.annotations.entity.Name;
import com.example.demowithtests.util.annotations.entity.ToLowerCase;
import com.example.demowithtests.util.exception.EmployeeNotFoundException;
import com.example.demowithtests.util.exception.InputParameterException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PassportService passportService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @ActivateCustomAnnotations({Name.class, ToLowerCase.class})
    // @Transactional(propagation = Propagation.MANDATORY)
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee createEM(Employee employee) {
        return entityManager.merge(employee);
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Page<Employee> getAllWithPagination(Pageable pageable) {
        Page<Employee> list = employeeRepository.findAll(pageable);
        return list;
    }

    @Override
    public Employee getById(Integer id) {
        idValidation(id);
        Optional<Employee> employeeOpt = findEmployee(employeeRepository.findById(id));
        return getNotDeletedEmployee(employeeOpt);
    }

    @Override
    public Employee updateById(Integer id, Employee employee) {
        Employee empById = getById(id);
        empById.setName(employee.getName());
        empById.setEmail(employee.getEmail());
        empById.setCountry(employee.getCountry());
        return employeeRepository.save(empById);
    }

    @Override
    public void removeById(Integer id) {
        Employee employee = getById(id);
        employee.setDeleted(true);
        employeeRepository.save(employee);
    }

    /**
     * Проверяет наличие в БД Employee
     *
     * @param employeeOptional, полученный из БД
     * @return Optional<Employee>
     * @throws ResourceNotFoundException, если записи нет в БД
     */
    private Optional<Employee> findEmployee(Optional<Employee> employeeOptional) throws ResourceNotFoundException {
        return Optional.of(employeeOptional.orElseThrow(ResourceNotFoundException::new));
    }

    /**
     * Проверяет, что переданный Employee не удален
     *
     * @param employeeOptional, полученный из БД
     * @return Employee
     * @throws EmployeeNotFoundException, если запись была ранее помечена is_deleted
     */
    private Employee getNotDeletedEmployee(Optional<Employee> employeeOptional) throws EmployeeNotFoundException {
        return employeeOptional.filter(emp -> !emp.isDeleted()).orElseThrow(EmployeeNotFoundException::new);
    }

    private void idValidation(Integer id) {
        if (id <= 0) {
            throw new InputParameterException();
        }
    }

    @Override
    public void removeAll() {
        employeeRepository.deleteAll();
    }

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
    public List<Employee> filterByCountry(String country) throws EmployeeNotFoundException {
        return employeeRepository.findByCountry(country).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Employee> filterNullEmails() throws EmployeeNotFoundException {
        return employeeRepository.findAllByEmailNull().orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Employee> filterLowerCaseCountries() throws EmployeeNotFoundException {
        return employeeRepository.findAllLowerCaseCountries().orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void updateLowerCaseCountriesToUpperCase() {
        employeeRepository.updateLowerCaseCountriesToUpperCase();
    }

    @Override
    public Employee handPassportToEmployee(Integer employeeId, Integer passportId, String photoLink) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(ResourceNotFoundException::new);
        employee.setPassport(passportService.handPassport(passportId, photoLink));
        return employeeRepository.save(employee);
    }
}
