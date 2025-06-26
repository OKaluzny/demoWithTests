package com.example.demowithtests.service.history;

import com.example.demowithtests.domain.Document;
import com.example.demowithtests.domain.History;
import com.example.demowithtests.repository.DocumentRepository;
import com.example.demowithtests.repository.HistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HistoryServiceBean implements HistoryService{

    private final HistoryRepository historyRepository;
    private final DocumentRepository documentRepository;

    @Override
    public History create(History history) {
        return historyRepository.save(history);
    }

    @Override
    public History create(String description, Document document) {
        History history = History
                .builder()
                .description(description)
                .document(document)
                .dateAndTime(LocalDateTime.now())
                .build();
        return historyRepository.save(history);
    }

    /**
     * Find a document with its history records by document ID
     * 
     * @param documentId the ID of the document to search for
     * @return the document with its history records
     */
    @Override
    public Optional<Document> findDocumentWithHistory(Integer documentId) {
        return documentRepository.findDocumentWithHistory(documentId);
    }

    /**
     * Find all documents with their history records
     * 
     * @return list of all documents with their history records
     */
    @Override
    public List<Document> findAllDocumentsWithHistory() {
        return documentRepository.findAllDocumentsWithHistory();
    }
}
