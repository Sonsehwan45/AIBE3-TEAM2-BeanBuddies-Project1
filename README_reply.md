# 🚀 트러블 슈팅
## reply 제거

```java
// 수정 전
replies.removeIf(reply -> reply.equals(replyId));

// 수정 후
replies.removeIf(reply -> reply.getId() == replyId);
```
수정 전처럼 코드를 잘못 작성 하였었다.

equals자체가 Object에서 선언된 메소드라 매개변수 타입 자체를 Object로 받아들여서

컴파일 에러가 발생하지 않았다.

한편, 수정 후를 작성하면서 BaseInitData의 각 table들의 키가 int로 선언된 것과

repository의 두번째 제네릭이 <Long>으로 선언되어 불일치한 것을 확인 할 수 있었다.

(repository의 두번쨰 매개변수 타입은 엔티티의 키 타입이다.)




## UI 변경

![img.png](img.png)

![img_1.png](img_1.png)

시행착오를 통해 그나마 괜찮은 형식으로 바꾸었다.

## `TransientObjectException `

비영속인 객체를 영속인 객체와 함께 저장하려고 하면 발생하는 예외

test에서 간단하게 객체들을 생성하고 persist 하지 않고 사용하려고 했다가 발생했다.

# 👀 기존 기능 버그 감지
## 1 대 다 연관관계 초기화 문제
```java
@OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
private List<Answer> answers = new ArrayList<>();
```

answers가 기존에 = new ArrayList<>(); 초기화 하지 않는 실수가 있었음

## ddl 자동생성 문제

view에서 content의 칼럼 설정이 없어, 자동생성된 content 칼럼이 varchar(255)로 선언됨

![img_2.png](img_2.png)

```java
@Column(columnDefinition = "TEXT")
private String content;
```