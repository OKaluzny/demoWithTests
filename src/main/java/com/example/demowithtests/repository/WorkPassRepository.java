package com.example.demowithtests.repository;

import com.example.demowithtests.domain.WorkPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkPassRepository extends JpaRepository<WorkPass, Integer> {

}
