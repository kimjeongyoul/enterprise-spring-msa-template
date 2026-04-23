# Infrastructure Blueprint (Level 1)

## 1. Overview
Platform V2의 기초가 되는 공통 인프라 계층을 정의한다. 이 계층은 서비스 간의 통신 규격, 에러 핸들링, 그리고 분산 트적을 위한 컨텍스트 관리를 담당한다.

## 2. Modules
- **common-core**: 
  - 모든 모듈의 최상위 의존성.
  - 외부 의존성을 최소화하고 순수 Java/Spring Framework 인터페이스 중심.
  - `ApiResponse`, `ErrorCode`, `TraceContext` 포함.
- **common-web**:
  - Spring MVC 기반 서비스들의 공통 설정.
  - Global Exception Handler, Feign Client 설정, 로깅 인터셉터.
- **gateway-service**:
  - Spring Cloud Gateway 기반의 API 관문.
  - Reactive 환경을 지원하며 `common-core`만 참조하여 MVC 의존성 충돌을 방지함.

## 3. Interfaces (Core)

### ApiResponse<T>
전사 공통 응답 규격.
```json
{
  "success": boolean,
  "data": T,
  "error": {
    "code": String,
    "message": String
  }
}
```

### TraceContext
ThreadLocal을 이용한 Trace-ID 관리. HTTP Header(`X-Trace-Id`)를 통해 서비스 간 전파된다.

## 4. Dependencies
- Java 17
- Spring Boot 3.2.4
- Spring Cloud 2023.0.0
