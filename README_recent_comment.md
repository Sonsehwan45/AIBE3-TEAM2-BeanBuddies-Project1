# 📂 프로젝트 파일 구조

```bash
jsb-project/
├── .gitignore
├── build.gradle.kts
├── gradlew
├── gradlew.bat
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/back/jsb/
    │   │       ├── JsbApplication.java
    │   │       ├── controller/
    │   │       │   ├── AnswerController.java
    │   │       │   ├── QuestionController.java
    │   │       │   └── UserController.java
    │   │       ├── dto/
    │   │       │   ├── AnswerForm.java
    │   │       │   ├── QuestionForm.java
    │   │       │   └── UserCreateForm.java
    │   │       ├── entity/
    │   │       │   ├── Answer.java
    │   │       │   ├── Question.java
    │   │       │   └── SiteUser.java
    │   │       ├── repository/
    │   │       │   ├── AnswerRepository.java
    │   │       │   ├── QuestionRepository.java
    │   │       │   └── UserRepository.java
    │   │       ├── security/
    │   │       │   ├── SecurityConfig.java
    │   │       │   └── UserSecurityService.java
    │   │       └── service/
    │   │           ├── AnswerService.java
    │   │           ├── QuestionService.java
    │   │           └── UserService.java
    │   └── resources/
    │       ├── application.properties
    │       ├── static/
    │       │   └── css/
    │       │       └── style.css
    │       └── templates/
    │           ├── answer_form.html
    │           ├── layout.html
    │           ├── login_form.html
    │           ├── question_detail.html
    │           ├── question_form.html
    │           ├── question_list.html
    │           └── signup_form.html
    └── test/
        └── java/
            └── com/back/jsb/
                └── ApplicationTests.java
