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
---
# 기본 기능 구현

## 1. 회원 기능

### 회원가입
<img width="1280" height="690" alt="회원가입" src="https://github.com/user-attachments/assets/93c191eb-be4b-4d04-be33-fbf58929d372" />

| 항목            | 내용                                                     |
| ------------- |--------------------------------------------------------|
| 🌐 **URL**    | `/user/sign`                                           |
| ⚙️ **Method** | `GET`, `POST`                                          |
| 🧾 **입력 필드**  | `username`, `nickname`, `password`, `passwordConfirm`  |

#### ✨ 기능 설명
- 새로운 사용자 등록

#### ⚠️ 예외 처리
- **Redirect + 안내 메세지**
    - 없음
- **폼 재렌더링 + 입력값 유지 + 필드별 오류**
    - 필드가 공백일 때
    - username/nickname이 중복될 때
    - 비밀번호가 불일치 할 때

---

### 로그인
<img width="1280" height="690" alt="로그인" src="https://github.com/user-attachments/assets/3def0fbb-5003-412f-b306-7506b4512223" />

| 항목            | 내용                     |
| ------------- | ---------------------- |
| 🌐 **URL**    | `/user/login`          |
| ⚙️ **Method** | `GET`, `POST`          |
| 🧾 **입력 필드**  | `username`, `password` `remember-me`|

#### ✨ 기능 설명
- `Spring Security` 사용
- 사용자 인증 후 로그인
- 로그인 유지(세션 관리)

#### ⚠️ 예외 처리
- **Redirect + 안내 메세지**
    - 로그인 실패 시
- **폼 재렌더링 + 입력값 유지 + 필드별 오류**
    - 없음

---

### 로그아웃
| 항목            | 내용             |
| ------------- | -------------- |
| 🌐 **URL**    | `/user/logout` |
| ⚙️ **Method** | `POST`         |

#### ✨ 기능 설명
- `Spring Security` 사용
- 사용자 로그아웃 처리
- 세션 종료

#### ⚠️ 예외 처리
- **Redirect + 안내 메세지**
    - 없음
- **폼 재렌더링 + 입력값 유지 + 필드별 오류**
    - 없음

---

## 2. 질문 기능

### 질문 등록
<img width="1280" height="690" alt="질문등록" src="https://github.com/user-attachments/assets/5e3d95ff-dc4e-4720-9fa4-d3848c95e2f6" />

| 항목            | 내용                 |
| ------------- | ------------------ |
| 🌐 **URL**    | `/question/write`  |
| ⚙️ **Method** | `GET`, `POST`      |
| 🧾 **입력 필드**  | `title`, `content` |

#### ✨ 기능 설명
- 새로운 질문 등록
- 로그인 한 사용자만 이용 가능

#### ⚠️ 예외 처리
- **Redirect + 안내 메세지**
    - 로그인하지 않고 post 요청을 보냈을 때
- **폼 재렌더링 + 입력값 유지 + 필드별 오류**
    - 입력 필드가 공백일 때

--

### 질문 목록 / 검색
<img width="1280" height="690" alt="질문목록" src="https://github.com/user-attachments/assets/7a375ba0-4da3-4d18-9fa1-a1391e83dd2d" />

| 항목             | 내용                                                           |
|----------------|--------------------------------------------------------------|
| 🌐 **URL**     | `/question/list`                                             |
| ⚙️ **Method**  | `GET`                                                        |
| 🔎 **쿼리 파라미터** | `types`(검색 조건: title, content, author, answer)<br>`keyword`(검색어) |


#### ✨ 기능 설명
- 질문 목록 조회
- 각 목록 아이템에는 `제목, 작성자, 작성일`이 표시됨
- 검색 조건(`types`)은 복수 선택 가능
    - 조건들은 OR로 연결되어 검색
    - ex) `types=title&types=content&keyword=스프링` : 제목이나 내용에 "스프링" 포함된 질문 검색
- 검색 후에도 기존 검색 조건/키워드가 유지

#### ⚠️ 예외 처리
- 검색어가 없으면 전체 목록 조회
- 검색 조건이 null일 경우 전체로 간주하고 목록 조회

---

### 질문 상세
<img width="1280" height="690" alt="질문상세" src="https://github.com/user-attachments/assets/1174e56d-d8df-49bd-a57b-4cb37be87e58" />

| 항목              | 내용                      |
| --------------- | ----------------------- |
| 🌐 **URL**      | `/question/detail/{id}` |
| ⚙️ **Method**   | `GET`                   |

#### ✨ 기능 설명
- 개별 질문 상세 정보 조회
- 질문 제목, 내용, 작성자, 작성일/수정일, 답변 목록 표시됨
- 로그인한 사용자라면 해당 질문에 답변 작성 가능
- 본인이 작성한 질문이라면 수정/삭제 버튼 노출

#### ⚠️ 예외 처리
- **Redirect + 안내 메세지**
    - 해당 질문글이 존재하지 않을 때
- **폼 재렌더링 + 입력값 유지 + 필드별 오류**
    - 없음

---

### 질문 수정 / 삭제
| 항목            | 내용                                               |
| ------------- | ------------------------------------------------ |
| 🌐 **URL**    | `/question/modify/{id}`, `/question/delete/{id}` |
| ⚙️ **Method** | `GET`, `POST`                                    |

#### ✨ 기능 설명
- 본인이 작성한 질문을 수정 / 삭제
- 수정 성공 시 질문 상세 페이지로 리다이렉트
- 삭제 성공 시 질묵 목록 페이지로 리다이렉트

#### ⚠️ 예외 처리
- **Redirect + 안내 메세지**
    - 본인이 아닌데 접근했을 때
    - 질문이 존재하지 않을 때
- **폼 재렌더링 + 입력값 유지 + 필드별 오류**
    - 수정 시 입력 필드가 공백일 때

---

## 3. 답변 기능
<img width="1280" height="690" alt="답변" src="https://github.com/user-attachments/assets/603aa8c2-0214-446a-a30a-f1f04cea27ac" />

### 답변 작성
| 항목            | 내용                            |
| ------------- | ----------------------------- |
| 🌐 **URL**    | `/answer/write/{question_id}` |
| ⚙️ **Method** | `POST`                        |
| 🧾 **입력 필드**  | `content`                     |

#### ✨ 기능 설명
- 질문 상세 페이지에서 답변 작성 가능
- 로그인 한 사용자에게만 답변 등록 버튼 활성화

#### ⚠️ 예외 처리
- **Redirect + 안내 메세지**
    - 로그인하지 않았을 때
    - 해당 질문이 존재하지 않을 때
- **폼 재렌더링 + 입력값 유지 + 필드별 오류**
    - 입력 필드가 공백일 때

---

### 답변 수정 / 삭제
| 항목            | 내용                                                                                     |
| ------------- | -------------------------------------------------------------------------------------- |
| 🌐 **URL**    | `/answer/modify/{question_id}/{answer_id}`, `/answer/delete/{question_id}/{answer_id}` |
| ⚙️ **Method** | `GET`, `POST`                                                                          |


#### ✨ 기능 설명
- 질문 상세 페이지에서 답변 수정/삭제 가능
- 본인이 작성한 답변의 경우 수정/삭제 버튼 노출

#### ⚠️ 예외 처리
- **Redirect + 안내 메세지**
    - 본인이 아닌데 접근했을 때
    - 해당 답변이 존재하지 않을 때
- **폼 재렌더링 + 입력값 유지 + 필드별 오류**
    - 수정 시 입력 필드가 공백일 때
---
## Q & A

1. Principal이 아니라 UserSecurity를 따로 만들어서 사용한 이유

Spring Secuirty가 제공하는 Principal의 경우 간단하게 username 정도만 가져올 수 있는 것으로 알고 있음  
nickname과 같이 따로 정의한 User 필드값을 가져오기 위해 UserDetails를 구현한 UserSecurity를 사용함

2. ReqestParam으로 값을 받지 않고 DTO로 받은 이유

Entity의 경우 값이 변경되면 DB에 직접적으로 반영되기 때문에 DTO로 받음  
이럴 경우 사용자에게 꼭 노출되어야 하는 부분만 떼어낼 수 있다는 장점이 있음
RequestParam이 아니라 DTO로 Form값을 직접 받을 경우,
DTO에서 걸어준 Validation이 적용되어 BindingResult를 통해 쉽게 오류를 처리할 수 있어 사용함

---

# 추가 기능 구현

## 프로필 - 이은주

### 구현 개요
- 회원가입 시 **프로필 사진 지정** 가능
- 프로필 페이지(`/user/profile`)에서 **조회 및 수정** 가능
  - 프로필 사진 업로드/삭제(기본 이미지)/변경 취소(기존 DB 이미지)
  - 닉네임 변경
  - DB 정보와 동일한 경우엔 저장 버튼 비활성화 = 변경 사항이 있을 때만 버튼 활성화
### 구현 방법
- `User` 엔티티에 `profileImage(BLOB)` 필드 추가
- `UserCreateFrom` DTO에 프로필 이미지(`MultipartFile`) 필드 추가
- `ProfileForm` DTO를 통해 프로필 이미지(`MultipartFile`), 닉네임, 삭제 여부를 전달
- `ImageService`에서 `Base64` 인코딩 처리
    - 뷰에서 `<img>` 태그에 바로 출력 가능하도록
- 프로필 이미지가 없으면 DB에는 `null`로 저장, 뷰에서는 기본 이미지 출력

### 스크린샷
<img width="1280" height="690" alt="프로필화면" src="https://github.com/user-attachments/assets/a24d8505-e801-4ca0-86ec-516965aa63bf" />


### 주요 코드
1. DTO - `ProfileForm`
```java
//수정된 값들을 받아오기 위한 DTO
public record ProfileForm (
        MultipartFile profileImage,
        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(max=10, message ="닉네임은 10자 이하여야 합니다.")
        String nickname,
        boolean deleteProfileImage //기본 이미지 변경 여부를 확인하기 위한 플래그
){
    public ProfileForm(User user) {
        this(
                null,
                user.getNickname(),
                false
        );
    }
}
```

2. 이미지 변환 - `ImageServie`
```java
@Service
public class ImageService {

    public String getBase64UserImage(User user) throws IOException {
        byte[] imageBytes;

        //DB에 저장된 이미지를 Base64로 인코딩 처리
        //DB에 저장된 값이 null일 경우 기본이미지로
        if(user.getProfileImage() != null) {
            imageBytes = user.getProfileImage();
        } else {
            ClassPathResource imgFile = new ClassPathResource("static/images/defaultProfile.png");
            imageBytes = imgFile.getInputStream().readAllBytes();
        }
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public String getBase64EditingImage(User user, MultipartFile newFile) throws IOException {
        
        //수정 중 변경하려했던 이미지를 Base64로 인코딩(오류나도 변경 이력을 유지한채로 폼을 재렌더링하기 위함)
        if (newFile != null && !newFile.isEmpty()) {
            return Base64.getEncoder().encodeToString(newFile.getBytes());
        }
        //수정 중인 이미지가 없을 경우 기본 이미지로 
        return getBase64UserImage(user);
    }
}
```

3. 프로필 수정 처리 - `UserController`
```java
    @PostMapping("/profile")
    public String modify(
            Model model,
            @Valid @ModelAttribute ProfileForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserSecurity userSecurity
    ) {
        User user = userSecurity.getUser();

        //공백 확인
        if (bindingResult.hasErrors()) {

            //DB이미지와 수정 요청했던 이미지를 Base64로 인코딩
            String originalBase64 = null;
            String currentBase64 =  null;
            try {
                originalBase64 = imageService.getBase64UserImage(user);
                currentBase64 = imageService.getBase64EditingImage(user, form.profileImage());
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("msg", "이미지 처리 중 오류가 발생했습니다.");
                return "redirect:/user/profile";
            }
            
            // 입력 필드에 공백이 있어 오류가 나더라도,
            // 기존 변경 이력들을 유지한 채로 폼을 재렌더링하기 위해 model에 저장
            model.addAttribute("profileImageOriginal", originalBase64);
            model.addAttribute("profileImageCurrent", currentBase64);
            model.addAttribute("originalNickname", user.getNickname());
            return "/profile";
        }

        userService.modify(user, form);
        redirectAttributes.addFlashAttribute("msg", "회원 정보가 변경되었습니다.");
        return "redirect:/user/profile";
    }
```


### Multipartfile과 Base64
1. `Multipartfile`
    - 정의 : 스프링에서 제공하는 파일 업로드 타입
    - 사용 이유 : `input type="file"`은 데이터를 바이너리로 전송하므로 `MultipartFile`로 받아야 함
    - 적용 : `ProfileForm`, `UserCreateForm`에서 `MultipartFile` 타입으로 `profileImage` 선언

2. `Base64`
   - 정의 : 바이너리 데이터를 텍스트로 인코딩하는 방식
   - 사용 이유 : DB에서 저장된 `byte[]` 이미지나 `MultipartFile` 이미지를 그대로 뷰에 넘기면 `<img>` 태그에서 표시 불가능
     - Base64로 인코딩 후 `data:imgae/png;base64...` 형태로 변환하여 출력
   - 적용 : `ImageServie`에서 이미지를 `Base64` 형태로 변환, `Controller`에서 `model`에 담아 뷰에 전달

### 트러블 슈팅
1. `MultipartFile`과 `byte[]` 타입 불일치
   - 문제 : 처음엔 `User` 엔티티의 `byte[]` 필드와 똑같이 받으려 했으나, 파일 업로드 시 `MultipartFile` 타입이 전달됨
   - 해결 : DTO에서는 `MultipartFile`로 받고, DTO를 Entity로 변환하는 과정에서 해당 이미지를 `btye[]`로 변환

2. 삭제/변경 구분 불가
    - 문제 : 수정 시 DB에 사진이 저장되어있는 경우, `사진 삭제`를 눌러 기본 이미지로 변경한 경우와 사진을 변경하지 않았을 경우 모두 null로 넘어가 구분이 불가능함
    - 해결 : `deleteProfileImage`라는 플래그 추가
      - true : 기본 이미지로 변경했다는 뜻이므로 DB에는 null로 저장
      - false : false일 경우 이미지가 변경되지 않았으므로 넘어온 값은 null이지만, 기존 이미지를 그대로 유지하면 됨(null로 변경하는 것이 아님!)

3. 닉네임 오류 발생 시 이미지 미리보기 유지 안됨
   - 문제 : 닉네임이 공백으로 넘어가 폼을 재렌더링할 경우, 기존에 변경한 이력(ex : 변경된 이미지)은 그대로 유지하고 싶었지만 원래 DB 이미지로만 재렌더링됨
   - 해결 : `profileImageCurrent`와 `profileImageOriginal` 둘 다 도입
       - `profileImageCurrent` : form에 담긴 변경하려했던 이미지를 Base64로 인코딩한 이미지
       - `profileImageOriginal` : 원래 DB이미지를 Base64로 인코딩한 이미지
       - 폼 재렌더링할 떄에는 Current를 넣어주고, 변경 이력을 확인하여 저장 버튼을 활성화할 땐 DB이미지를 기준으로 하도록 변경

## 비밀번호 변경 및 초기화 - 최원제

## 개요
사용자의 **비밀번호 수정** 기능과 **비밀번호 초기화** 기능을 구현하였습니다.  
비밀번호 초기화 시에는 회원 가입 시 입력한 이메일로 초기화된 비밀번호가 발송되도록 하였습니다.

---

## 세부 설명

### 1. 비밀번호 수정
<img width="760" height="68" alt="image" src="https://github.com/user-attachments/assets/52c444b8-6832-45db-b389-1cb1c7b7b28b" />

로그인 시 상단 메뉴바에서 비밀번호 변경 항목을 확인할 수 있습니다.



<img width="883" height="499" alt="image" src="https://github.com/user-attachments/assets/0f98d080-f0eb-45c5-ac19-058b2670906d" />

비밀번호 변경 화면에는 다음과 같은 입력창이 있습니다.
- 기존 비밀번호
- 신규 비밀번호
- 신규 비밀번호 확인

입력 후 **변경 버튼**을 누르면 비밀번호가 변경됩니다.  
비밀번호 변경 조건은 다음과 같습니다.

- 기존 비밀번호가 올바르게 입력되어야 함
- 신규 비밀번호는 기존 비밀번호와 동일하지 않아야 함
- 신규 비밀번호 확인 입력란과 동일해야 함.

---

### 2. 비밀번호 초기화
<img width="868" height="429" alt="image" src="https://github.com/user-attachments/assets/e06532fe-57ca-456e-bc39-4762efa4d289" />

로그인 화면 아래의 링크를 통해 비밀번호 초기화 화면으로 들어갈 수 있습니다.


<img width="886" height="370" alt="image" src="https://github.com/user-attachments/assets/e27ab86e-8f45-439b-9fb5-37d715bfc5b5" />

비밀번호 초기화 과정은 다음과 같습니다.

1. 초기화 화면에서 계정 아이디 입력
2. 존재하지 않는 계정일 경우 오류 반환
3. 정상 계정일 경우 비밀번호 초기화 진행
4. 변경된 비밀번호가 계정 이메일로 발송됨

<img width="349" height="252" alt="image" src="https://github.com/user-attachments/assets/27eea808-113f-4116-8203-f78b47e78b91" />

발송된 이메일은 위의 사진과 같습니다.

---

## 기타 변경 사항 및 참고 사항

### User Entity 수정
- User Entity에 `email` 속성을 추가하였습니다.
- `initData`에서도 User 생성 시 이메일을 넣도록 수정하였습니다.

### SMTP 설정
비밀번호 초기화 기능 구현을 위해 **Naver SMTP**를 사용하였습니다.  
이를 위한 설정은 `application-mail.yml` 파일에 작성되며, 보안 상 해당 파일은 깃허브에 업로드하지 않았습니다.  
아래 예시를 참고하여 직접 작성해야 합니다.


```YAML
spring:                           # 예시에서는 네이버 SMTP 사용중
  mail:
    host: smtp.naver.com          # 네이버 SMTP 서버 주소
    port: 465                     # 네이버 SMTP 포트 번호
    username: xxxxxxx@naver.com    # 네이버 이메일
    password: xxxxxxxxxxx          # 네이버 비밀번호
    properties:
      mail:
        smtp:
          auth: true              # 사용자 인증 여부
          ssl:
            enable: true
            trust: smtp.naver.com
```

관련 참고 링크:  
- [[Java] NAVER 메일 SMTP 환경 설정 방법](https://adjh54.tistory.com/596)

<img width="802" height="197" alt="image" src="https://github.com/user-attachments/assets/4ea8c105-54bd-4d36-bb5e-799e07729048" />


> ⚠️ **주의:**  
> `yml` 파일의 `spring.mail.password`는 네이버 계정 비밀번호가 아닌,  
> POP3/SMTP 사용 설정 시 발급할 수 있는 **전용 어플리케이션 비밀번호**를 사용해야 합니다.
