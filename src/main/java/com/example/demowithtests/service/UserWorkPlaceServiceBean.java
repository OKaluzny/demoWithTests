package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.UsersWorkPlaces;
import com.example.demowithtests.domain.UsersWorkPlacesKey;
import com.example.demowithtests.domain.WorkPlace;
import com.example.demowithtests.repository.UserWorkPlacesRepository;
import com.example.demowithtests.util.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

@Service
@RequiredArgsConstructor
public class UserWorkPlaceServiceBean implements UserWorkPlaceService {

    private final UserWorkPlacesRepository userWorkPlacesRepository;

    @Override
    public UsersWorkPlaces create(Employee employee, WorkPlace workPlace) {

        UsersWorkPlaces userWorkPlace = UsersWorkPlaces.builder()
                .usersWorkPlacesId(buildUsersWorkPlacesKey(employee, workPlace))
                .employee(employee)
                .workPlace(workPlace)
                .build();

        employee.setUsersWorkPlaces(addUsersWorkPlaces(userWorkPlace));

        return userWorkPlacesRepository.save(userWorkPlace);
    }

    @Override
    public void deactivateWorkPlace(UsersWorkPlaces usersWorkPlace) {
        usersWorkPlace.setIsActive(Boolean.FALSE);
        userWorkPlacesRepository.save(usersWorkPlace);
    }

    private UsersWorkPlacesKey buildUsersWorkPlacesKey(Employee employee, WorkPlace workPlace) {
        return UsersWorkPlacesKey.builder()
                .employeeId(employee.getId())
                .workPlacesId(workPlace.getId())
                .build();
    }

    private Set<UsersWorkPlaces> addUsersWorkPlaces(UsersWorkPlaces userWorkPlace) {
        Employee employee = userWorkPlace.getEmployee();
        WorkPlace workPlace = userWorkPlace.getWorkPlace();

        Integer employeeId = employee.getId();
        Integer workPlaceId = workPlace.getId();

        Set<UsersWorkPlaces> employeeUsersWorkPlaces = employee.getUsersWorkPlaces();

        if (!employeeUsersWorkPlaces.isEmpty()) {
            // бросает исключение, если у Employee уже есть 3 WorkPlace
            if (employeeUsersWorkPlaces.size() == 3 && !employeeUsersWorkPlaces.contains(userWorkPlace)) {
                List<Integer> workplacesId = employeeUsersWorkPlaces.stream()
                        .map(u -> u.getWorkPlace().getId())
                        .toList();
                throw new CustomException("EMPLOYEE=" + employeeId + " HAS MAX WORKPLACES=" + workplacesId);
            }

            // бросает исключение если Workplace уже активен у данного Employee
            employeeUsersWorkPlaces.stream()
                    .filter(u -> userWorkPlace.equals(u) && Boolean.TRUE.equals(u.getIsActive()))
                    .findAny()
                    .ifPresent(u -> {
                        throw new CustomException("WORKPLACE=" + workPlaceId + " FOR EMPLOYEE=" + employeeId + " ALREADY ACTIVE");
                    });

            // меняет активный Workplace на "false"
            employeeUsersWorkPlaces.stream()
                    .filter(u -> Boolean.TRUE.equals(u.getIsActive()))
                    .findAny()
                    .ifPresent(u -> u.setIsActive(Boolean.FALSE));
        }

        employeeUsersWorkPlaces.add(userWorkPlace);
        return employeeUsersWorkPlaces;
    }

}