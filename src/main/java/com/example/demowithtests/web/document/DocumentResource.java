package com.example.demowithtests.web.document;

import com.example.demowithtests.domain.Document;


public interface DocumentResource {

    Document createDocument(Document document);

    Document getDocumentById(Integer id);
}
