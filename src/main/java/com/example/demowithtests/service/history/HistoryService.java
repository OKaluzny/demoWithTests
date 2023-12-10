package com.example.demowithtests.service.history;

import com.example.demowithtests.domain.Document;
import com.example.demowithtests.domain.History;

public interface HistoryService {
    History create(History history);
    History create(String description, Document document);
}
