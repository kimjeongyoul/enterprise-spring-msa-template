package com.msa.platform.auth.service;

import com.msa.platform.auth.domain.Outbox;
import com.msa.platform.auth.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxScheduler {

    private final OutboxRepository outboxRepository;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publishEvents() {
        List<Outbox> events = outboxRepository.findByProcessedFalse();
        
        for (Outbox event : events) {
            try {
                log.info("Publishing event: {} - Payload: {}", event.getEventType(), event.getPayload());
                
                // 여기서 실제 Kafka 등으로 전송 로직이 들어감
                // kafkaTemplate.send("user-events", event.getPayload());

                event.markProcessed();
            } catch (Exception e) {
                log.error("Failed to publish event: {}", event.getId(), e);
            }
        }
    }
}
