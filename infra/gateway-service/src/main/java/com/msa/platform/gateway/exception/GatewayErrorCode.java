package com.msa.platform.gateway.exception;

import com.msa.platform.core.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GatewayErrorCode implements ErrorCode {
    INVALID_TOKEN("G001", "유효하지 않은 토큰입니다.", 401),
    EXPIRED_TOKEN("G002", "만료된 토큰입니다.", 401),
    UNAUTHORIZED("G003", "인증 정보가 없습니다.", 401),
    FORBIDDEN("G004", "권한이 없습니다.", 403);

    private final String code;
    private final String message;
    private final int status;
}
