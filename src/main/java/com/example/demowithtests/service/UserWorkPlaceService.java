package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.UsersWorkPlaces;
import com.example.demowithtests.domain.WorkPlace;

public interface UserWorkPlaceService {
    UsersWorkPlaces create(Employee employee, WorkPlace workPlace);

    void deactivateWorkPlace(UsersWorkPlaces usersWorkPlace);

}