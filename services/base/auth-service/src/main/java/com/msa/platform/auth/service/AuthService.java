package com.msa.platform.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.platform.auth.domain.Outbox;
import com.msa.platform.auth.domain.User;
import com.msa.platform.auth.dto.SignUpRequest;
import com.msa.platform.auth.repository.OutboxRepository;
import com.msa.platform.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void signUp(SignUpRequest request) {
        // 1. 사용자 저장
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword()) // 실제로는 인코딩 필요
                .name(request.getName())
                .role("USER")
                .build();
        userRepository.save(user);

        // 2. Outbox 이벤트 저장 (동일 트랜잭션)
        try {
            String payload = objectMapper.writeValueAsString(user);
            Outbox outbox = Outbox.builder()
                    .aggregateType("USER")
                    .aggregateId(user.getId())
                    .eventType("USER_CREATED")
                    .payload(payload)
                    .processed(false)
                    .createdAt(LocalDateTime.now())
                    .build();
            outboxRepository.save(outbox);
        } catch (Exception e) {
            throw new RuntimeException("Event serialization failed", e);
        }
    }
}
