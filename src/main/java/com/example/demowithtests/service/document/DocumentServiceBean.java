package com.example.demowithtests.service.document;

import com.example.demowithtests.domain.Document;
import com.example.demowithtests.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
