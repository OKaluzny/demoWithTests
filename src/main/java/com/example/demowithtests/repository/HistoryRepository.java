package com.example.demowithtests.repository;

import com.example.demowithtests.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
}
