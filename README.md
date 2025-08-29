# JSB 질문/답변 게시판 구현

## 📂 프로젝트 파일 구조

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
                ├── question/question_detail.html # 질문 상세 (답변 출력, 등록, 삭제 기능 포함)
                ├── answer/answer_form.html       # 답변 수정
                ├── user/login_form.html          # 로그인
                └── user/signup_form.html         # 회원가입        
```

## 💡 특이사항

- Spring security를 활용한 회원 로그인/로그아웃 기능 구현에 문서를 참고하였습니다. [링크](https://wikidocs.net/162150)
- 로그인 후 header에서 사용자 이름을 표시할 때, Spring Security의 인증 객체(Authentication)에 담긴 로그인 사용자 정보를 Thymeleaf를 통해 출력하도록 구현함.