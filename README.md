# 📂 프로젝트 파일 구조

```bash
# 📂 프로젝트 파일 구조

```bash
jsb-project/
├── build.gradle.kts                  # Gradle 빌드 설정 파일
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/back/
    │   │       ├── Application.java              # 메인 애플리케이션
    │   │       ├── domain/
    │   │       │   ├── controller/              # API 컨트롤러
    │   │       │   │   ├── AnswerController.java
    │   │       │   │   ├── QuestionController.java
    │   │       │   │   └── SiteUserController.java
    │   │       │   ├── dto/                     # DTO 클래스
    │   │       │   │   ├── AnswerForm.java
    │   │       │   │   ├── QuestionForm.java
    │   │       │   │   └── UserCreateForm.java
    │   │       │   ├── entity/                  # 도메인 엔티티
    │   │       │   │   ├── Answer.java
    │   │       │   │   ├── Question.java
    │   │       │   │   └── SiteUser.java
    │   │       │   ├── repository/              # JPA 리포지토리
    │   │       │   │   ├── AnswerRepository.java
    │   │       │   │   ├── QuestionRepository.java
    │   │       │   │   └── SiteUserRepository.java
    │   │       │   └── service/                 # 비즈니스 로직
    │   │       │       ├── AnswerService.java
    │   │       │       ├── QuestionService.java
    │   │       │       └── SiteUserService.java
    │   │       └── global/                      # 공통 컴포넌트
    │   │           ├── jpa/
    │   │           │   └── entity/
    │   │           │       └── BaseEntity.java   # 공통 엔티티
    │   │           └── security/                 # 보안 설정
    │   │               ├── SecurityConfig.java
    │   │               └── UserSecurityService.java
    │   └── resources/
    │       ├── application.yml                   # 애플리케이션 설정
    │       ├── static/
    │       │   └── css/
    │       │       └── style.css                # 스타일시트
    │       └── templates/                       # Thymeleaf 템플릿
    │           ├── layout.html
    │           ├── login_form.html
    │           ├── question_detail.html
    │           ├── question_form.html
    │           ├── question_list.html
    │           └── signup_form.html
    └── test/
        └── java/
            └── com/back/
                └── ApplicationTests.java         # 테스트 클래스
```
