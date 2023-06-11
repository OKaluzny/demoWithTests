package com.example.demowithtests.service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface EntityCheckingService<T> {

    default List<T> checkListIsEmpty(List<T> employeeList) {
        if (employeeList.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return employeeList;
    }
}

