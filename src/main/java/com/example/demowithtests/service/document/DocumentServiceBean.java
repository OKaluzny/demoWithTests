package com.example.demowithtests.service.document;

import com.example.demowithtests.domain.Document;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceBean implements DocumentService {

    private final DocumentRepository documentRepository;

    /**
     * @param document
     * @return
     */
    @Override
    public Document create(Document document) {
        document.setExpireDate(LocalDateTime.now().plusYears(5));
        return documentRepository.save(document);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Document getById(Integer id) {
        return documentRepository.findById(id).orElseThrow();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Document handlePassport(Integer id) {
        Document document = getById(id);
        if (document.getIsHandled()) {
            throw new RuntimeException();
        } else document.setIsHandled(Boolean.TRUE);
        return documentRepository.save(document);
    }

    /**
     * @param passportId
     * @param imageId
     * @return
     */
    @Override
    public Document addImage(Integer passportId, Integer imageId) {
        return null;
    }

    /**
     * Find a document by its number
     * 
     * @param number the document number to search for
     * @return the document with the specified number
     */
    @Override
    public Optional<Document> findByNumber(String number) {
        return documentRepository.findByNumber(number);
    }

    /**
     * Find a document by its UUID
     * 
     * @param uuid the UUID to search for
     * @return the document with the specified UUID
     */
    @Override
    public Optional<Document> findByUuid(String uuid) {
        return documentRepository.findByUuid(uuid);
    }

    /**
     * Find all documents that expire before the specified date
     * 
     * @param date the date to compare against
     * @return list of documents expiring before the date
     */
    @Override
    public List<Document> findByExpireDateBefore(LocalDateTime date) {
        return documentRepository.findByExpireDateBefore(date);
    }

    /**
     * Find all documents that expire before the specified date with pagination
     * 
     * @param date the date to compare against
     * @param pageable pagination information
     * @return page of documents expiring before the date
     */
    @Override
    public Page<Document> findByExpireDateBefore(LocalDateTime date, Pageable pageable) {
        return documentRepository.findByExpireDateBefore(date, pageable);
    }

    /**
     * Find all documents that expire after the specified date
     * 
     * @param date the date to compare against
     * @return list of documents expiring after the date
     */
    @Override
    public List<Document> findByExpireDateAfter(LocalDateTime date) {
        return documentRepository.findByExpireDateAfter(date);
    }

    /**
     * Find all documents that expire between the specified dates
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of documents expiring between the dates
     */
    @Override
    public List<Document> findByExpireDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return documentRepository.findByExpireDateBetween(startDate, endDate);
    }

    /**
     * Find all documents by their handled status
     * 
     * @param isHandled the handled status to search for
     * @return list of documents with the specified handled status
     */
    @Override
    public List<Document> findByIsHandled(Boolean isHandled) {
        return documentRepository.findByIsHandled(isHandled);
    }

    /**
     * Find all documents by their handled status with pagination
     * 
     * @param isHandled the handled status to search for
     * @param pageable pagination information
     * @return page of documents with the specified handled status
     */
    @Override
    public Page<Document> findByIsHandled(Boolean isHandled, Pageable pageable) {
        return documentRepository.findByIsHandled(isHandled, pageable);
    }

    /**
     * Find a document by its associated employee
     * 
     * @param employee the employee to search for
     * @return the document associated with the employee
     */
    @Override
    public Optional<Document> findByEmployee(Employee employee) {
        return documentRepository.findByEmployee(employee);
    }

    /**
     * Find all documents with numbers containing the specified text
     * 
     * @param text the text to search for in document numbers
     * @return list of documents with numbers containing the text
     */
    @Override
    public List<Document> findByNumberContaining(String text) {
        return documentRepository.findByNumberContaining(text);
    }

    /**
     * Find all documents with numbers containing the specified text with pagination
     * 
     * @param text the text to search for in document numbers
     * @param pageable pagination information
     * @return page of documents with numbers containing the text
     */
    @Override
    public Page<Document> findByNumberContaining(String text, Pageable pageable) {
        return documentRepository.findByNumberContaining(text, pageable);
    }

    /**
     * Count documents by their handled status
     * 
     * @param isHandled the handled status to count
     * @return the count of documents with the specified handled status
     */
    @Override
    public long countByIsHandled(Boolean isHandled) {
        return documentRepository.countByIsHandled(isHandled);
    }

    /**
     * Find documents that will expire before the specified date
     * 
     * @param date the date to compare against
     * @return list of documents expiring before the specified date
     */
    @Override
    public List<Document> findDocumentsExpiringWithinDays(LocalDateTime date) {
        return documentRepository.findDocumentsExpiringWithinDays(date);
    }
}
