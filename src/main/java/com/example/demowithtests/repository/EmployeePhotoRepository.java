package com.example.demowithtests.repository;

import com.example.demowithtests.domain.EmployeePassport;
import com.example.demowithtests.domain.EmployeePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePhotoRepository extends JpaRepository<EmployeePhoto, Integer> {

}
