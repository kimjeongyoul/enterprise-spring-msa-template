package com.msa.platform.web.exception;

import com.msa.platform.core.dto.ApiResponse;
import com.msa.platform.core.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("Unhandled Exception: ", e);
        return ResponseEntity
                .internalServerError()
                .body(ApiResponse.fail("500", e.getMessage()));
    }

    /**
     * 비즈니스 로직 예외 처리
     */
    public static class BusinessException extends RuntimeException {
        private final String code;
        private final String message;
        private final int status;

        public BusinessException(ErrorCode errorCode) {
            super(errorCode.getMessage());
            this.code = errorCode.getCode();
            this.message = errorCode.getMessage();
            this.status = errorCode.getStatus();
        }

        public BusinessException(String code, String message, int status) {
            super(message);
            this.code = code;
            this.message = message;
            this.status = status;
        }

        public String getCode() { return code; }
        @Override
        public String getMessage() { return message; }
        public int getStatus() { return status; }
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        log.warn("Business Exception: {}", e.getMessage());
        return ResponseEntity
                .status(e.getStatus())
                .body(ApiResponse.fail(e.getCode(), e.getMessage()));
    }
}
