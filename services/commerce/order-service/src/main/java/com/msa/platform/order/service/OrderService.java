package com.msa.platform.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.platform.order.domain.Order;
import com.msa.platform.order.domain.OrderOutbox;
import com.msa.platform.order.repository.OrderOutboxRepository;
import com.msa.platform.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderOutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void createOrder(Long productId, Integer quantity, Long amount, String userId) {
        // 1. 주문 생성 (PENDING 상태)
        Order order = Order.builder()
                .userId(userId)
                .productId(productId)
                .quantity(quantity)
                .amount(amount)
                .status(Order.OrderStatus.PENDING)
                .build();
        orderRepository.save(order);

        // 2. Saga 시작 이벤트 저장
        try {
            String payload = objectMapper.writeValueAsString(order);
            OrderOutbox outbox = OrderOutbox.builder()
                    .eventType("ORDER_CREATED")
                    .payload(payload)
                    .processed(false)
                    .createdAt(LocalDateTime.now())
                    .build();
            outboxRepository.save(outbox);
        } catch (Exception e) {
            throw new RuntimeException("Saga start failed", e);
        }
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        orderRepository.findById(orderId).ifPresent(Order::cancel);
    }

    @Transactional
    public void completeOrder(Long orderId) {
        orderRepository.findById(orderId).ifPresent(Order::complete);
    }
}
