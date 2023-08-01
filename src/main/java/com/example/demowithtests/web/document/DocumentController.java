package com.example.demowithtests.web.document;

import com.example.demowithtests.domain.Document;
import com.example.demowithtests.service.document.DocumentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class DocumentController implements DocumentResource {

    private final DocumentService documentService;

    /**
     * @param document
     * @return
     */
    @Override
    @PostMapping("/documents")
    @ResponseStatus(HttpStatus.CREATED)
    public Document createDocument(@RequestBody Document document) {
        return documentService.create(document);
    }

    /**
     * @param id
     * @return
     */
    @Override
    @GetMapping("/documents/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Document getDocumentById(@PathVariable Integer id) {
        return documentService.getById(id);
    }
}
