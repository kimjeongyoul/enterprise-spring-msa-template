package com.msa.platform.gateway.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.platform.core.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@Order(-2)
@RequiredArgsConstructor
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBuffer dataBuffer = response.bufferFactory().allocateBuffer();
            try {
                ApiResponse<Void> apiResponse = ApiResponse.fail("GATEWAY_ERROR", ex.getMessage());
                byte[] bytes = objectMapper.writeValueAsBytes(apiResponse);
                dataBuffer.write(bytes);
            } catch (Exception e) {
                log.error("Error writing response", e);
            }
            return dataBuffer;
        }));
    }
}
