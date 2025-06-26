package com.example.demowithtests.service.history;

import com.example.demowithtests.domain.Document;
import com.example.demowithtests.domain.History;

import java.util.List;
import java.util.Optional;

public interface HistoryService {
    History create(History history);
    History create(String description, Document document);

    /**
     * Find a document with its history records by document ID
     * 
     * @param documentId the ID of the document to search for
     * @return the document with its history records
     */
    Optional<Document> findDocumentWithHistory(Integer documentId);

    /**
     * Find all documents with their history records
     * 
     * @return list of all documents with their history records
     */
    List<Document> findAllDocumentsWithHistory();
}
