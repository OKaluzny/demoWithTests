package com.example.demowithtests.repository;

import com.example.demowithtests.domain.EmployeePassport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePassportRepository extends JpaRepository<EmployeePassport, Integer> {

}
