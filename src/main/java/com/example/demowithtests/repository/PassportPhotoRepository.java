package com.example.demowithtests.repository;

import com.example.demowithtests.domain.PassportPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportPhotoRepository extends JpaRepository<PassportPhoto, Integer> {

}
