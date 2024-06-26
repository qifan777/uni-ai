package io.qifan.server.ai.model.service;

import io.qifan.server.ai.model.repository.AiModelRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AiModelService {
    private final AiModelRepository aiModelRepository;

}