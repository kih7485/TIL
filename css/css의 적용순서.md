### 상속(inheritance)
- 상속이란 상위(부모, 조상) 요소에 적용된 프로퍼티를 하위(자식, 자손) 요소가 물려 받는 것을 의미한다. 상속 기능이 없다면 각 요소의 Rule set에 프로퍼티를 매번 각각 지정해야 한다.

- 하지만 모든 프로퍼티가 상속되는 것은 아니다. 프로퍼티 중에는 상속이 되는 것과 되지 않는 것이 있다.

참조: https://developer.mozilla.org/en-US/docs/web/css/inheritance

### !important
css는 기본적으로 속성을 중복으로 주면 나중에 준 속성으로 적용이 된다.<br/>
그래서 속성 뒤에 !important를 주면 후행을 무시하고 !important 속성이 우선적으로 적용된다.
```css
p{
    color: red
}
p{
    color: yellow !important;
}
```