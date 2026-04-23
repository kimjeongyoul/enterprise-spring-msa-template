package com.msa.platform.order.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_outbox")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderOutbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType;
    
    @Column(columnDefinition = "TEXT")
    private String payload;

    private boolean processed;
    private LocalDateTime createdAt;

    public void markProcessed() {
        this.processed = true;
    }
}
