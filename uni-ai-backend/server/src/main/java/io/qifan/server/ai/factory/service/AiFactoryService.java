package io.qifan.server.ai.factory.service;

import io.qifan.server.ai.factory.repository.AiFactoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AiFactoryService {
    private final AiFactoryRepository aiFactoryRepository;

}