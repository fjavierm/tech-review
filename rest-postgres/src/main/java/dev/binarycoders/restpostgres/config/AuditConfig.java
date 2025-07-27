package dev.binarycoders.restpostgres.config;

import dev.binarycoders.restpostgres.repository.entity.PersonEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;

import java.time.LocalDateTime;

@Configuration
public class AuditConfig {

    @Bean
    public BeforeConvertCallback<PersonEntity> auditTimestamps() {
        return entity -> {
            final var now = LocalDateTime.now();

            if (entity.getId() == null) {
                entity.setCreatedAt(now);
            }

            entity.setUpdatedAt(now);

            return entity;
        };
    }
}
