package com.example.my_jwt.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditListener implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
    }
}
