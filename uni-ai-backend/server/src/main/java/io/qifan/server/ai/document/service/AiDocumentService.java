package io.qifan.server.ai.document.service;

import io.qifan.server.ai.document.repository.AiDocumentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AiDocumentService {
    private final AiDocumentRepository aiDocumentRepository;

}