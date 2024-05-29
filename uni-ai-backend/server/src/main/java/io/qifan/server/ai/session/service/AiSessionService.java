package io.qifan.server.ai.session.service;

import io.qifan.server.ai.session.repository.AiSessionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AiSessionService {
    private final AiSessionRepository aiSessionRepository;

}