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

컴파일 에러가 발생하지 않아 버그감지에 시간이 좀 걸렸다.

## 테스트코드 작성

테스트 코드 작성 자체보다는 테스트 환경설정이 가장 큰 문제가 되었고, 시간이 많이 소요되었다.

ApplicationRunner를 통해 test프로필로 데이터를 초기화 하는 방법은 예외가 발생했는데 해결하지 못하였다.

`@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)`를 테스트 클래스에 추가하여

해결하긴 하였으나, 컨텍스트를 지속적으로 초기화 하여 테스트가 상당히 느리다.

다른 방법으로도 해결할 수 있으면 좋을 것 같다.

# 👀 기존 기능 버그 감지
## BaseInitData PK값 타입
BaseInitData의 각 entity들의 키가 int로 선언된 것과

repository의 두번째 제네릭이 <Long>으로 선언되어 불일치한 것을 확인 할 수 있었다.

▶️ repository의 두번쨰 매개변수 타입은 엔티티의 키 타입이다.