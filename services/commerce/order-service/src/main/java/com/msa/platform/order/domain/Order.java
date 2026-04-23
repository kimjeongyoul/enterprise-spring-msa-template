package com.msa.platform.order.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private Long productId;
    private Integer quantity;
    private Long amount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void cancel() {
        this.status = OrderStatus.CANCELED;
    }

    public void complete() {
        this.status = OrderStatus.COMPLETED;
    }

    public enum OrderStatus {
        PENDING, COMPLETED, CANCELED
    }
}
