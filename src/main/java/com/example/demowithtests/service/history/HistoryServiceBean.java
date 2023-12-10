package com.example.demowithtests.service.history;

import com.example.demowithtests.domain.Document;
import com.example.demowithtests.domain.History;
import com.example.demowithtests.repository.HistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class HistoryServiceBean implements HistoryService{

    private final HistoryRepository historyRepository;

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
}
