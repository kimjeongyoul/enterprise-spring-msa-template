package com.msa.platform.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.platform.core.dto.ApiResponse;
import com.msa.platform.web.exception.GlobalExceptionHandler.BusinessException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        try (InputStream inputStream = response.body().asInputStream()) {
            ApiResponse<?> apiResponse = objectMapper.readValue(inputStream, ApiResponse.class);
            String code = apiResponse.getError().getCode();
            String message = apiResponse.getError().getMessage();
            int status = response.status();
            
            log.error("Feign Call Failed: {} - {} : {}", methodKey, code, message);
            
            return new BusinessException(code, message, status);
        } catch (Exception e) {
            return new ErrorDecoder.Default().decode(methodKey, response);
        }
    }
}
