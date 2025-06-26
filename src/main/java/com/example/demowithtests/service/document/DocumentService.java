package com.example.demowithtests.service.document;

import com.example.demowithtests.domain.Document;
import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DocumentService {

    Document create(Document document);

    Document getById(Integer id);

    Document handlePassport(Integer id);

    Document addImage(Integer passportId, Integer imageId);

    Optional<Document> findByNumber(String number);

    Optional<Document> findByUuid(String uuid);

    List<Document> findByExpireDateBefore(LocalDateTime date);

    Page<Document> findByExpireDateBefore(LocalDateTime date, Pageable pageable);

    List<Document> findByExpireDateAfter(LocalDateTime date);

    List<Document> findByExpireDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Document> findByIsHandled(Boolean isHandled);

    Page<Document> findByIsHandled(Boolean isHandled, Pageable pageable);

    Optional<Document> findByEmployee(Employee employee);

    List<Document> findByNumberContaining(String text);

    Page<Document> findByNumberContaining(String text, Pageable pageable);

    long countByIsHandled(Boolean isHandled);

    List<Document> findDocumentsExpiringWithinDays(LocalDateTime date);
}
