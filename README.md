# 📂 프로젝트 파일 구조

```bash
# 📂 프로젝트 파일 구조

```bash
jsb-project/
├── build.gradle.kts                  # Gradle 빌드 설정
└── src/
    └── main/
        ├── java/
        │   └── com/back/
        │       ├── Application.java              # 메인 클래스
        │       ├── domain/
        │       │   ├── controller/               # 컨트롤러
        │       │   ├── dto/                      # DTO
        │       │   ├── entity/                   # 엔티티
        │       │   ├── repository/               # 리포지토리
        │       │   └── service/                  # 서비스
        │       └── global/
        │           ├── jpa/entity/               # 공통 엔티티
        │           └── security/                 # 보안 설정
        │           └── web/WebConfig.java        # HTTP method 확장 설정
        └── resources/
            ├── application.yml                   # 설정 파일
            ├── static/css/style.css              # CSS
            └── templates/
                ├── layout.html                   # 전체 레이아웃
                ├── fragments/header.html         # 헤더 fragment
                ├── question/question_list.html   # 질문 목록
                ├── question/question_form.html   # 질문 작성
                ├── question/question_detail.html # 질문 상세
                ├── user/login_form.html          # 로그인
                └── user/signup_form.html         # 회원가입
```
