package com.example.bookmanager.domain.listener;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public interface Auditable {
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();

    void setCreatedAt(LocalDateTime createdAt);
    void setUpdatedAt(LocalDateTime updatedAt);

}
