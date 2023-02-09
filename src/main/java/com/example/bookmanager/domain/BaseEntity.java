package com.example.bookmanager.domain;

import com.example.bookmanager.domain.listener.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@Data
@MappedSuperclass //해당클래스의 필드를 상속 받는 클래스의 column으로 포함시켜주겠다는 선언
@EntityListeners(value = AuditingEntityListener.class)
public class BaseEntity implements Auditable {
    @Column(nullable = false, updatable = false, columnDefinition = "datetime(6) default now(6)")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false, columnDefinition = "datetime(6) default now(6)")
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
