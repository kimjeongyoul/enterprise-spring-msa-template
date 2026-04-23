package com.msa.platform.notification.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserEventListener {

    /**
     * 회원가입 이벤트 수신 및 알림 발송 시뮬레이션
     */
    public void onUserCreated(String payload) {
        log.info("Received UserCreatedEvent: {}", payload);
        log.info("Sending Welcome Email/SMS to user...");
    }
}
