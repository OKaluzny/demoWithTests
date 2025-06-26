package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Document;
import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    /**
     * Find a document by its number
     * 
     * @param number the document number to search for
     * @return the document with the specified number
     */
    Optional<Document> findByNumber(String number);

    /**
     * Find a document by its UUID
     * 
     * @param uuid the UUID to search for
     * @return the document with the specified UUID
     */
    Optional<Document> findByUuid(String uuid);

    /**
     * Find all documents that expire before the specified date
     * 
     * @param date the date to compare against
     * @return list of documents expiring before the date
     */
    List<Document> findByExpireDateBefore(LocalDateTime date);

    /**
     * Find all documents that expire before the specified date with pagination
     * 
     * @param date the date to compare against
     * @param pageable pagination information
     * @return page of documents expiring before the date
     */
    Page<Document> findByExpireDateBefore(LocalDateTime date, Pageable pageable);

    /**
     * Find all documents that expire after the specified date
     * 
     * @param date the date to compare against
     * @return list of documents expiring after the date
     */
    List<Document> findByExpireDateAfter(LocalDateTime date);

    /**
     * Find all documents that expire between the specified dates
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of documents expiring between the dates
     */
    List<Document> findByExpireDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find all documents by their handled status
     * 
     * @param isHandled the handled status to search for
     * @return list of documents with the specified handled status
     */
    List<Document> findByIsHandled(Boolean isHandled);

    /**
     * Find all documents by their handled status with pagination
     * 
     * @param isHandled the handled status to search for
     * @param pageable pagination information
     * @return page of documents with the specified handled status
     */
    Page<Document> findByIsHandled(Boolean isHandled, Pageable pageable);

    /**
     * Find a document by its associated employee
     * 
     * @param employee the employee to search for
     * @return the document associated with the employee
     */
    Optional<Document> findByEmployee(Employee employee);

    /**
     * Find all documents with numbers containing the specified text
     * 
     * @param text the text to search for in document numbers
     * @return list of documents with numbers containing the text
     */
    List<Document> findByNumberContaining(String text);

    /**
     * Find all documents with numbers containing the specified text with pagination
     * 
     * @param text the text to search for in document numbers
     * @param pageable pagination information
     * @return page of documents with numbers containing the text
     */
    Page<Document> findByNumberContaining(String text, Pageable pageable);

    /**
     * Count documents by their handled status
     * 
     * @param isHandled the handled status to count
     * @return the count of documents with the specified handled status
     */
    long countByIsHandled(Boolean isHandled);

    /**
     * Find documents that will expire before the specified date
     * 
     * @param date the date to compare against
     * @return list of documents expiring before the specified date
     */
    @Query("SELECT d FROM Document d WHERE d.expireDate BETWEEN CURRENT_DATE AND :date")
    List<Document> findDocumentsExpiringWithinDays(@Param("date") LocalDateTime date);

    /**
     * Find a document with its history records by document ID
     * 
     * @param documentId the ID of the document to search for
     * @return the document with its history records
     */
    @Query("SELECT d FROM Document d LEFT JOIN FETCH d.history WHERE d.id = :documentId")
    Optional<Document> findDocumentWithHistory(@Param("documentId") Integer documentId);

    /**
     * Find all documents with their history records
     * 
     * @return list of all documents with their history records
     */
    @Query("SELECT d FROM Document d LEFT JOIN FETCH d.history")
    List<Document> findAllDocumentsWithHistory();
}
