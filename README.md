# Enterprise Spring MSA Template (Modular Platform V2)

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-brightgreen)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.0-blue)

이 프로젝트는 특정 비즈니스에 종속되지 않고, 모든 산업군(금융, 커머스, 물류 등)에서 즉시 가져다 사용할 수 있도록 설계된 **"Modular MSA Platform"** 표준 템플릿입니다.

## 🎯 Project Vision: "Standardized Foundation for MSA"
대부분의 MSA 프로젝트는 인프라 설정과 공통 로직(인증, 에러 핸들링, 추적)을 구축하는 데 초기 비용의 40% 이상을 소모합니다. 이 템플릿은 그 비용을 제로화하고, 개발자가 **"비즈니스 도메인 로직"**에만 집중할 수 있는 완벽한 환경을 제공합니다.

---

## 🏗️ Layered Architecture (Tiered Design)

플랫폼은 의존성 결합을 최소화하기 위해 3개의 물리적 계층으로 분리되어 있습니다.

### **Level 1: Infra (The Engine)**
- **`common-core`**: 전사 표준 응답(`ApiResponse`), 에러 인터페이스, 분산 트레이싱(`TraceContext`)을 포함한 최상위 모듈.
- **`common-web`**: Spring MVC 기반 서비스들의 공통 설정(예외 처리기, 로깅 인터셉터, Feign 설정).
- **`gateway-service`**: WebFlux 기반 관문. 리액티브 환경에서도 표준 응답 규격을 유지하는 커스텀 에러 핸들링 포함.
- **`common-service`**: DB 기반 메타데이터(공통 코드) 서빙 엔진.

### **Level 2: Base Services (The Starter)**
- **`auth-service`**: JWT 기반 보안 및 **Transactional Outbox 패턴**이 적용된 회원가입 표준 모델.
- **`notification-service`**: 비동기 이벤트를 수신하여 알림을 발송하는 EDA(Event-Driven Architecture) 모델.

### **Level 3: Verticals (Business Case)**
- **`commerce-vertical` (Order, Product, Payment)**: **Choreography Saga 패턴**을 통해 분산 트랜잭션을 해결하는 고난도 비즈니스 구현체 예시.

---

## 🛡️ Key Solutions (Best Practices)

1.  **Dependency Isolation**: 게이트웨이(WebFlux)와 일반 서비스(MVC) 간의 의존성 충돌을 물리적 모듈 분리로 완벽 해결.
2.  **Transactional Outbox**: DB 트랜잭션과 메시지 발행의 원자성을 보장하여 데이터 정합성 유지.
3.  **Saga Pattern (Choreography)**: 주문-재고-결제 간의 분산 트랜잭션 및 보상 트랜잭션 로직 표준화.
4.  **Global Traceability**: `X-Trace-Id` 전파를 통한 전 서비스 구간 로깅 추적.
5.  **Standard API Format**: 모든 에러와 성공 응답을 전사 표준 규격(`ApiResponse`)으로 통일.

---

## 🚀 Getting Started (How to use)

### 새로운 산업군 서비스 추가하기
본인의 도메인(예: `Logistics`)을 이 플랫폼 위에 올리려면 다음 단계를 따르세요.

1.  **모듈 생성**: `services/logistics/delivery-service` 생성.
2.  **Gradle 등록**: `settings.gradle`에 `include` 추가.
3.  **인프라 상속**: `build.gradle`에 `implementation project(':infra:common-web')` 추가.
4.  **도메인 집중**: 인프라 설정 없이 바로 도메인 비즈니스 로직 개발 시작.

---

## 🛠 Engineering Standard
이 프로젝트는 **Spec-Driven Development**를 지향합니다.
- 모든 구현의 근거는 `specs/` 폴더 내 명세서에 있습니다.
- `ai-spec-kit` 표준을 준수하며, 명세(SSOT)가 코드보다 우선합니다.

## 📜 Platform Guide
더 자세한 확장 및 브랜치 전략은 [specs/platform-guide.md](./specs/platform-guide.md)를 참고하세요.
