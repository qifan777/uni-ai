package io.qifan.server.ai.tag.root.service;

import io.qifan.server.ai.tag.root.repository.AiTagRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AiTagService {
    private final AiTagRepository aiTagRepository;

}