# 🚀 트러블 슈팅
## 최근 답변

```html

<html layout:decorate="~{layout}">
<div layout:fragment="content" class="page-container">
    <div th:each="answer : ${answers}">
        <!--   문제가 된 부분 StackOverFlow     -->
        <div th:field="${answer.question}">

        </div>
        <div th:field="${answer.content}">

        </div>
    </div>
</div>
```

answer -> question -> question이 List<answer>를 가져 다시 처음의 answer를 참조 -> 무한 재귀

🔎 th:field는 내부적으로 `Spring DataBinder`의 바인딩 기능을 사용하기 위해 객체그래프 탐색을 시도한다.

👉 대신에 th:text를 통해 필요한 answer.question.toString()만 호출 하도록 한다.

## UI 변경

![img.png](img.png)

![img_1.png](img_1.png)

시행착오를 통해 그나마 괜찮은 형식으로 바꾸었다.