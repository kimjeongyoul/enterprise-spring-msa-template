package com.msa.platform.payment.service;

import com.msa.platform.payment.domain.Payment;
import com.msa.platform.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public void processPayment(Long orderId, Long amount) {
        Payment payment = Payment.builder()
                .orderId(orderId)
                .amount(amount)
                .status("PENDING")
                .build();
        paymentRepository.save(payment);

        // 결제 시뮬레이션
        if (amount > 1000000) { // 예시: 100만원 넘으면 결제 실패
            payment.fail();
            throw new RuntimeException("Payment limit exceeded");
        }

        payment.complete();
        log.info("Payment completed for order: {}", orderId);
    }
}
