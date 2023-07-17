package com.example.demowithtests.repository;

import com.example.demowithtests.domain.UsersWorkPlaces;
import com.example.demowithtests.domain.UsersWorkPlacesKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWorkPlacesRepository extends JpaRepository<UsersWorkPlaces, UsersWorkPlacesKey> {

}