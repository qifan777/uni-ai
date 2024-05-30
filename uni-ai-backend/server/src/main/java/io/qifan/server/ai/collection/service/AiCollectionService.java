package io.qifan.server.ai.collection.service;

import io.qifan.server.ai.collection.repository.AiCollectionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AiCollectionService {
    private final AiCollectionRepository aiCollectionRepository;

}