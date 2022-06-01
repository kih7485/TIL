```css
li{ //선택자
    color:red;//선언
    text-decoration:underline;
    //key는 속성, value는 속성값 으로 부름
}
```

### id 선택자
- 특정 요소에 id를 주고 css를 변경하고 싶을 떄 다음과 같이 # 형태로 css를 선언한다.
```html
<style>
    #select{
        color:red
    }
</style>
<ul>
    <li>1</li>
    <li id="select">2</li>
    <li>3</li>
</ul>
```

### 부모 자식 선택자
li > ul

### 가상 클래스 선택자
- 각각 엘리먼트의 상태에 따라 스타일을 줌.
- css는 동시에 선언하면 후선언 한 스타일이 적용됨.

- :link - 방문한 적이 없는 링크
- :visited - 방문한 적이 있는 링크
- visited는 보안상 이슈로 사용할 수 있는 속성이 제한적이다.
- :hover - 마우스를 롤오버 했을 때
- :active - 마우스를 클릭했을 때