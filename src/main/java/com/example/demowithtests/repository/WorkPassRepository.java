package com.example.demowithtests.repository;

import com.example.demowithtests.domain.EmployeePassport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkPassRepository extends JpaRepository<EmployeePassport, Integer> {

}
