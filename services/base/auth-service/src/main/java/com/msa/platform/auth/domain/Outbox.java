package com.msa.platform.auth.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "outbox")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Outbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aggregateType;
    private Long aggregateId;
    private String eventType;
    
    @Column(columnDefinition = "TEXT")
    private String payload;

    private boolean processed;
    private LocalDateTime createdAt;

    public void markProcessed() {
        this.processed = true;
    }
}
