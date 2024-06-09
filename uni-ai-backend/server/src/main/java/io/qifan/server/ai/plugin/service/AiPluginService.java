package io.qifan.server.ai.plugin.service;

import io.qifan.server.ai.plugin.repository.AiPluginRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AiPluginService {
    private final AiPluginRepository aiPluginRepository;

}