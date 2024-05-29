package io.qifan.infrastructure.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnProperty(prefix = "security", name = "enabled", havingValue = "true")
public class AuthAutoConfiguration {
    @Configuration
    @Import(WebConfig.class)
    public static class SecurityConfig {

    }
}
