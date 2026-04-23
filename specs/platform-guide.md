# Modular MSA Platform 활용 가이드

이 플랫폼은 어떤 산업군(Vertical)에서도 즉시 MSA 환경을 구축할 수 있도록 설계된 **Modular Template**입니다.

## 1. 계층별 역할
- **Level 1 (Infra)**: `common-core`, `common-web`, `gateway`. 모든 서비스의 공통 언어와 관문을 담당합니다.
- **Level 2 (Base)**: `auth`, `notification`. 서비스 운영에 필수적인 공통 도메인입니다.
- **Level 3 (Verticals)**: `commerce`, `finance` 등 실제 비즈니스가 구현되는 곳입니다.

## 2. 새로운 산업군(Vertical) 추가하기
본인의 도메인(예: `logistics`)을 추가하려면 다음 단계를 따르세요.

### Step 1: 모듈 생성
`services/` 디렉토리 하위에 새로운 폴더를 생성합니다.
```bash
mkdir -p services/logistics/delivery-service
```

### Step 2: Gradle 등록
`settings.gradle` 파일에 새 모듈을 추가합니다.
```gradle
include 'services:logistics:delivery-service'
```

### Step 3: 표준 인프라 상속
새 모듈의 `build.gradle`에 `common-web`을 추가합니다. 이것만으로 **표준 응답 규격, 전역 에러 핸들링, 분산 트레이싱**이 자동 적용됩니다.
```gradle
dependencies {
    implementation project(':infra:common-web')
}
```

### Step 4: 게이트웨이 등록
`infra/gateway-service`의 설정에서 새 서비스로의 경로를 지정합니다.

## 3. 브랜치 및 업데이트 전략
- **인프라 업데이트**: `main` 브랜치에서 제공되는 `infra` 및 `base` 모듈의 업데이트를 본인의 작업 브랜치로 `merge`하여 최신 기능을 유지합니다.
- **산업군 분리**: 각 산업군은 `services/` 하위의 독립된 폴더로 관리되므로, 필요한 모듈만 `include`하여 빌드 크기를 최적화할 수 있습니다.
