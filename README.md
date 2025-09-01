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

👉 대신에 

## ![img.png](img.png)