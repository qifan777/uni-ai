package io.qifan.server.ai.role.service;

import io.qifan.server.ai.role.repository.AiRoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AiRoleService {
    private final AiRoleRepository aiRoleRepository;

}