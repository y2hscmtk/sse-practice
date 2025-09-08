# SSE-Practice-BE API 문서

이 문서는 SSE-Practice 백엔드 API에 대한 명세를 제공합니다.

## 공통 응답 형식

모든 API 응답은 아래의 JSON 형식을 따릅니다.

```json
{
  "isSuccess": true,
  "code": "COMMON200",
  "message": "성공입니다.",
  "result": {
    // API별 개별 데이터
  }
}
```

-   `isSuccess`: `true` 또는 `false`로 요청의 성공 여부를 나타냅니다.
-   `code`: 응답의 상태를 나타내는 고유 코드입니다. (예: `COMMON200`, `MEMBER4001`)
-   `message`: 응답에 대한 설명 메시지입니다.
-   `result`: 실제 데이터가 담기는 부분입니다. 실패 시 `null`일 수 있습니다.

---

## 1. 회원 API (`/api/member`)

### 1.1. 회원가입

-   **`POST /api/member/join`**
-   **설명**: 새로운 사용자를 등록합니다.
-   **요청 헤더**: `Content-Type: application/json`
-   **요청 본문**:

    ```json
    {
      "loginId": "user123",
      "password": "password123!"
    }
    ```

-   **성공 응답 (`200 OK`)**:
    -   **응답 헤더**: `Authorization: Bearer <jwt-token>`
    -   **응답 본문**: `result`에 JWT 토큰이 담겨 반환됩니다.

-   **실패 응답 (`403 Forbidden`)**: 이미 존재하는 아이디일 경우

### 1.2. 로그인

-   **`POST /api/member/login`**
-   **설명**: 아이디와 비밀번호로 로그인하고 JWT 토큰을 발급받습니다.
-   **요청 헤더**: `Content-Type: application/json`
-   **요청 본문**:

    ```json
    {
      "loginId": "user123",
      "password": "password123!"
    }
    ```

-   **성공 응답 (`200 OK`)**:
    -   **응답 헤더**: `Authorization: Bearer <jwt-token>`
    -   **응답 본문**: `result`에 JWT 토큰이 담겨 반환됩니다.

-   **실패 응답**: 존재하지 않는 아이디 또는 잘못된 비밀번호일 경우

### 1.3. 내 정보 조회

-   **`GET /api/member/me`**
-   **설명**: 현재 로그인된 사용자의 정보를 조회합니다. **(인증 필요)**
-   **요청 헤더**: `Authorization: Bearer <jwt-token>`
-   **성공 응답 (`200 OK`)**:
    -   **응답 본문**:

        ```json
        {
          "isSuccess": true,
          "code": "COMMON200",
          "message": "성공입니다.",
          "result": {
            "loginId": "user123",
            "authority": "ROLE_USER"
          }
        }
        ```

### 1.4. 전체 사용자 목록 조회

-   **`GET /api/member/list`**
-   **설명**: 모든 사용자의 목록을 조회합니다. **(인증 필요)**
-   **요청 헤더**: `Authorization: Bearer <jwt-token>`
-   **성공 응답 (`200 OK`)**:
    -   **응답 본문**:

        ```json
        {
          "isSuccess": true,
          "code": "COMMON200",
          "message": "성공입니다.",
          "result": [
            {
              "loginId": "user123",
              "authority": "ROLE_USER"
            },
            {
              "loginId": "admin123",
              "authority": "ROLE_ADMIN"
            }
          ]
        }
        ```

### 1.5. 사용자 권한 수정

-   **`PATCH /api/member/{memberId}/authority`**
-   **설명**: 특정 사용자의 권한을 수정합니다. **(인증 필요)**
-   **Path Parameter**: `memberId` (사용자의 고유 ID)
-   **요청 헤더**:
    -   `Authorization: Bearer <jwt-token>`
    -   `Content-Type: application/json`
-   **요청 본문**:

    ```json
    {
      "authority": "ROLE_ADMIN"
    }
    ```

-   **성공 응답 (`200 OK`)**:
    -   **응답 본문**: 수정된 사용자의 정보가 반환됩니다.

        ```json
        {
          "isSuccess": true,
          "code": "COMMON200",
          "message": "성공입니다.",
          "result": {
            "loginId": "user123",
            "authority": "ROLE_ADMIN"
          }
        }
        ```

-   **실패 응답**:
    -   `400 Bad Request` (MEMBER4001): `memberId`에 해당하는 사용자가 없을 경우
