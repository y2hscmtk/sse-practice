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
-   **요청 헤더**:
    -   `Content-Type: application/json`
-   **요청 본문**:

    ```json
    {
      "loginId": "user123",
      "password": "password123!"
    }
    ```

-   **성공 응답 (`200 OK`)**:
    -   **응답 헤더**:
        -   `Authorization: Bearer <jwt-token>`
    -   **응답 본문**:

        ```json
        {
          "isSuccess": true,
          "code": "COMMON200",
          "message": "성공입니다.",
          "result": "Bearer <jwt-token>"
        }
        ```

-   **실패 응답 (`403 Forbidden`)**:
    -   **설명**: 이미 존재하는 아이디로 가입을 시도할 경우 발생합니다.
    -   **응답 본문**:

        ```json
        {
          "isSuccess": false,
          "code": "MEMBER4003",
          "message": "해당하는 사용자가 이미 존재합니다.",
          "result": "회원가입에 실패하였습니다."
        }
        ```

### 1.2. 로그인

-   **`POST /api/member/login`**
-   **설명**: 아이디와 비밀번호로 로그인하고 JWT 토큰을 발급받습니다.
-   **요청 헤더**:
    -   `Content-Type: application/json`
-   **요청 본문**:

    ```json
    {
      "loginId": "user123",
      "password": "password123!"
    }
    ```

-   **성공 응답 (`200 OK`)**:
    -   **응답 헤더**:
        -   `Authorization: Bearer <jwt-token>`
    -   **응답 본문**:

        ```json
        {
          "isSuccess": true,
          "code": "COMMON200",
          "message": "성공입니다.",
          "result": "Bearer <jwt-token>"
        }
        ```

-   **실패 응답**:
    -   **설명**: 존재하지 않는 아이디 또는 잘못된 비밀번호로 로그인을 시도할 경우 발생합니다.
    -   **응답 본문 (예시: 사용자 없음)**:

        ```json
        {
          "isSuccess": false,
          "code": "MEMBER4001",
          "message": "해당하는 사용자를 찾을 수 없습니다.",
          "result": null
        }
        ```

    -   **응답 본문 (예시: 비밀번호 불일치)**:

        ```json
        {
          "isSuccess": false,
          "code": "MEMBER4002",
          "message": "비밀번호가 일치하지 않습니다.",
          "result": null
        }
        ```

### 1.3. 내 정보 조회

-   **`GET /api/member/me`**
-   **설명**: 현재 로그인된 사용자의 정보를 조회합니다. **(인증 필요)**
-   **요청 헤더**:
    -   `Authorization: Bearer <jwt-token>`
-   **요청 본문**: 없음
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

-   **실패 응답 (`401 Unauthorized`)**:
    -   **설명**: 인증 토큰이 없거나 유효하지 않을 경우 발생합니다.
    -   **응답 본문**:

        ```json
        {
          "isSuccess": false,
          "code": "TOKEN4001",
          "message": "토큰이 없거나 만료 되었습니다.",
          "result": null
        }
        ```
