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

## 🔐 SpringSecurity

### SecurityFilterChain

스프링 빈 컨테이너에 `SecurityFilterChain`을 등록함으로써, 사용자의 권한에 따른 인가를 설정할 수 있다.

### BCryptPasswordEncoder

> BCryptPasswordEncoder 클래스는 비크립트(BCrypt) 해시 함수를 사용하는데,
> 비크립트는 해시 함수의 하나로 주로 비밀번호와 같은 보안 정보를 안전하게 저장하고 검증할 때 사용하는 암호화 기술이다.
>
> 점프 투 스프링부트 3.06

`SecurityFilterChain.encoed()`에 문자열을 전달함으로써 암호화 가능하다.

```java
BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
String encodedPw = passwordEncoder.encode(password);
```

매번 인코더의 인스턴스를 생성하는 대신, @Bean으로 등록함으로써 재사용 가능하다.

### UserRole의 ROLE_는 컨벤션

<details><summary> AI 요약 </summary>
Spring Security에서 역할(Role)에 ROLE_ 접두사를 사용하는 것은 규칙이자 컨벤션이며, 이를 통해 Spring Security가 사용자의 권한을 명확하게 인식하고 처리할 수 있기 때문입니다.

ROLE_ 접두사의 중요성
명시적인 역할 식별: Spring Security는 내부적으로 ROLE_로 시작하는 문자열을 "권한(Authority)"이 아닌 "역할(Role)"로 인식하도록 설계되었습니다. 예를 들어, hasRole('
ADMIN')과 같은 표현식은 ROLE_ADMIN 권한을 가진 사용자를 찾습니다.

인증 및 인가 처리: Spring Security는 ROLE_ 접두사를 보고 해당 권한이 사용자의 역할을 나타낸다는 것을 자동으로 이해하고, 역할 기반 접근 제어(role-based access control)를
적용합니다. 이 접두사가 없으면 Spring Security가 이를 단순한 권한(GrantedAuthority)으로만 인식하여 hasRole()과 같은 메서드가 제대로 작동하지 않을 수 있습니다.

따라서, UserRole.ADMIN을 ROLE_ADMIN으로 정의하는 것은 Spring Security 프레임워크와의 약속이자, 개발자가 의도한 역할 기반 보안 기능을 정확하게 구현하기 위한 필수적인 방법입니다.


</details>

### UserService는 UserDetailsService를 상속하여 구현

`loadUserByUsername`을 구현해야한다.
- DB에서 유저 정보를 찾아내고 username, password, `Collection<GrantedAuthority>`를 전달하여 UserDetails를 반환해야 한다.
- `GrantedAuthority`의 구현체인 `SimpleGrantedAuthority`의 생성자에 권한을 나타내는 문자열 ROLE_~를 전달하면 된다.
- 이러한 ROLE_A, ROLE_B와 같은 문자열은 컨트롤러 메소드에 `@PreAuthorize("hasRole('A')")`를 붙여 페이지 접근 권한을 부여하거나,
- `<div sec:authorize="hasRole('ADMIN')">`를 통해 페이지에서 특정 부분만 노출되도록 할 수 있다.


반환값인 UserDetails는 구현체로 User와 MutableUser가 있다.

| 특징         | **`User` (불변)**                         | **`MutableUser` (가변)**                        |
|:-----------|:----------------------------------------|:----------------------------------------------|
| **변경 가능성** | **불변(Immutable)**: 한 번 생성되면 변경할 수 없습니다. | **변경 가능(Mutable)**: setter 메서드로 값을 바꿀 수 있습니다. |
| **주요 용도**  | **일반적인 표준** 인증 및 권한 부여에 사용됩니다.          | 테스트 또는 특별한 경우에만 제한적으로 사용됩니다.                  |
| **보안**     | **매우 안전**합니다.                           | 의도치 않은 변경 위험이 있어 **주의가 필요**합니다.               |
| **권장 상태**  | **적극 권장**되며, Spring Security의 표준입니다.    | 제한적인 경우를 제외하고 사용하지 않는 것이 좋습니다.                |                                 |


### AuthenticationManager

인증을 처리하는 객체. 빈 컨테이너에 등록된 `UserSecurityService`와 `PasswordEncoder`를 내부적으로 사용한다.

마찬가지로 Bean에 등록하여 사용하자.

### login, logout POST 요청은 컨트롤러 작성할 필요 없음

`SpringConfig`에서 사용자가 설정한 `SecurityFilterChain`의 정보에 따라 인터셉트되어 처리된다.

뷰 템플릿에서의 action이나 href의 경로만 잘 맞도록 설정해주면 된다.

```java
    http
                .formLogin(formLogin -> formLogin
                        .loginPage("/siteusers/login")
                        .defaultSuccessUrl("/"))
                .logout(logout -> logout
                        .logoutUrl("/siteusers/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true));
```


## 📚 추가 학습한 것

### @NotEmpty VS @NotBlank

| 어노테이션           | 대상                                     | Null 허용 여부 | 빈 값(`""`) 허용 여부 | 공백(`"   "`) 허용 여부 |
|:----------------|:---------------------------------------|:-----------|:----------------|:------------------|
| **`@NotEmpty`** | `String`, `Collection`, `Map`, `Array` | ❌          | ❌               | ✅                 |
| **`@NotBlank`** | `String`                               | ❌          | ❌               | ❌                 |

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

### 이름이 동일한 어노테이션 뭘 써야할까?

@Id
- JPA표준인 Jakrta를 사용해야한다.
- Spring은 @ID 특정 DB에 사용되는 어노테이션이다.

@Transactional
- 기능이 많은 Spring을 사용하면 된다.

### bindingResult.rejectValue의 두번째 매개변수 errorCode

`messages.properties`에서 정의된 메시지를 찾아 매핑하여 반환한다.

```java
passwordInCorrect=패스워드와 확인패스워드가일치하지 않습니다.
...
```

## 🚀 트러블슈팅

### 삭제처리

**과정**

삭제를 위해 코드를 작성하는 도중 JPARepository에 `delete`, `deleteById` 두 가지 메소드가 있는 것을 발견했다.

이에 service에서 새로 정의한 findById -> delete로 진행하는 대신 바로 deleteById를 사용할지 고민했다.

한편, deleteById를 살펴보니 내부적으로 JpaRepository.findById를 사용하고 있었다.

그래서 이러한 상황에서 내가 service에서 예외 메시지를 정의한 findById를 재사용 할지, repository의 deleteById를
사용하는 것이 좋을지 AI에게 피드백 받았다.

동작의 큰 차이는 없지만 간결성 VS 예외메시지 일관성에 대한 의견을 듣고 싶었다.

```java
public Question findById(Integer id) {
    return questionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_BY_ID.formatted(id)));
}
```

그런데 이 과정에서 existById라는 새로운 메소드를 추천 받았고, existById -> deleteById가 DB 접근 횟수 2회,
내가 제시한 코드는 DB접근 횟수 3회라는 피드백을 해주었다.

그리고 1회의 DB접근으로 해결가능한 JPQL 직접 작성하기도 제시하였다.

**결론**

일단 existById는 SQL로 `SELECT 1 FROM question WHERE id = ?`와 같은 SQL을 작성해 디스크나 메모리에서 Entity의 값을 불러오는 findById보다
빠른 존재 여부를 조회할 수 있다.

그런데, deleteById 자체가 다시 findById를 호출하므로
이 코드가 DB 접근 횟수 3회의 코드, 반대로 처음 제시한 코드가 2회가 된다.

논지가 살짝 바뀌어서 existById를 사용할지 말지에 대한 고민으로 바뀌었지만 결국 트러블슈팅 과정속에서

내부코드와 JPA 동작에 대해 리마인드하며 deleteById가 findById -> delete보다 특별히 최적화가 이루어지는 것이 아니라는 확신이 들었고

내가 service에서 선언한 findById를 사용하는 것이 좋겠다는 결론을 내리게 됐다.

### answer저장

관계의 주인인 answer가 아닌 question에서 참조변수를 지정하려고 하였다.

```java
// 변경 전
public Answer write(Integer questionId, AnswerForm answerForm) {
    Question question = questionService.findById(questionId);
    Answer answer = new Answer(answerForm.content());
    question.getAnswers().add(answer);

    return answerRepository.save(answer);
}


// 변경 후 
public Answer write(Integer questionId, AnswerForm answerForm) {
    Question question = questionService.findById(questionId);
    Answer answer = new Answer(answerForm.content(), question);

    return answerRepository.save(answer);
}
```

### logout처리

책에 나와있는대로 a태그를 통해 logout 처리를 했지만 제대로 처리되지 않았다.

logout은 post요청을 보내야만 제대로 인터셉트되어 처리된다고 한다.

