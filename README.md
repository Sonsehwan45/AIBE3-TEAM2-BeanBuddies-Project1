## 🛠️ 구현 범위

### ⚙️ 기본 프로젝트 세팅

- Spring Initializr를 활용해 프로젝트 생성
- 필수 Dependencies:
    - 🌐 Spring Web
    - 🎨 Thymeleaf
    - 🗄️ Spring Data JPA
    - 💾 H2 Database (또는 MySQL)
    - 🔄 Spring Boot DevTools
    - 🌶️Lombok

---

## 📝 기본 기능

### 📋 게시판 기능 (Q&A)

아래 기능은 필수로 구현 하여야 합니다.

- 질문
    - 질문 목록 보기
    - 질문 등록 ✍️
    - 질문 상세 보기 🔎
    - 질문 수정 ✏️
    - 질문 삭제 🗑️
- 답변
    - 답변 등록
    - 답변 수정
    - 답변 삭제
- 검색 기능 🔍 (제목/내용 기준 검색)

### 회원 기능

- 회원가입 ✨
- 로그인 & 로그아웃 🔐 (Spring Security 활용)

---


## 📂 프로젝트 파일 구조

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
```


## 📚 추가 학습한 것

### @NotEmpty VS @NotBlank

| 어노테이션 | 대상 | Null 허용 여부 | 빈 값(`""`) 허용 여부 | 공백(`"   "`) 허용 여부 |
| :--- | :--- | :--- | :--- | :--- |
| **`@NotEmpty`** | `String`, `Collection`, `Map`, `Array` | ❌ | ❌ | ✅ |
| **`@NotBlank`** | `String` | ❌ | ❌ | ❌ |

### SiteUser의 경우 수정일이 불필요한데 BaseEntity를 상속해도 좋을까? 
(생각 변경됨 : 비밀번호 변경등 수정사항 충분히 발생할 수 있음)

AI에게 질문한 결과 설계의 일관성을 제외한 부분이 부정적이었음


<details><summary> AI의 근거 </summary>

📌 장점

관리가 단순하다 → 하나의 BaseEntity만 있으면 됨

모든 엔티티에 createDate, modifyDate가 보장되므로 쿼리/조회 시 일관성 있음
(예: 최근 수정된 순 정렬 같은 기능을 거의 모든 도메인에 바로 적용 가능)

스프링 데이터 JPA의 Auditing 기능을 한 번만 적용하면 된다.

📌 단점

실제로 수정일이 필요 없는 엔티티에도 modifyDate 컬럼이 생김
→ DB 설계 상 “이 필드는 왜 있지?” 라는 의문을 남길 수 있음

ISP 위반
클라이언트가 사용하지 않는 기능에 의존하게 됨
SiteUser는 modifyDate가 필요 없는데 강제로 가지게 됨

도메인 모델 관점에서 불필요한 속성이 섞여 있음

데이터베이스 리소스 낭비

📌 적합한 상황

프로젝트 성격상 거의 모든 엔티티가 수정일을 갖는 경우
(예: 게시글, 댓글, 주문, 상품, 결제 등 대부분 수정될 수 있는 비즈니스 도메인)

운영 편의성(정렬, 로그 추적 등)이 일관성을 더 중요하게 여길 때

</details>

```java
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CreatedDate
    private LocalDateTime createDate;
}
@MappedSuperclass
public abstract class BaseTimeEntity extends BaseEntity {

    @LastModifiedDate
    private LocalDateTime modifyDate;
}
```

대신 다음과 같은 계층적 구조를 추천함

현재 Entity가 총 3개밖에 없고 Answer와 Question만 id, 생성일, 수정일을 갖고 있으므로

이러한 계층적 구조를 도입하지는 않고 Answer와 Question만 3개의 속성을 갖는 BaseEntity를 상속하도록 함.

❗ SiteUser또한 비밀번호 변경 등 수정이 일어날 가능성이 있으므로, 나중에 정말 불변한 Entity가 생긴다면
계층적 구조를 고려하여 보자.

### @Id인 필드에 Wrapper VS Primitive

null 값을 부여할 수 있는 Wrapper 클래스를 사용하는 것이 권장된다.

[하이버네이트 사용자 가이드](https://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html#entity-pojo-constructor)

