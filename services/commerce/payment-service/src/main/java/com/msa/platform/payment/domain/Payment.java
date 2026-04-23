package com.msa.platform.payment.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private Long amount;
    private String status;

    public void complete() {
        this.status = "COMPLETED";
    }

    public void fail() {
        this.status = "FAILED";
    }
}
