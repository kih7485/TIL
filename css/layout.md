### boxmodel
- CSS는 박스의 크기, 위치, 속성(색, 배경, 테두리 모양 등)을 결정.


### box-sizing
- 각각의 div에 같은 넓이를 주어도 border가 다른 경우 엘리먼트 속성 외에 나머지가 달라져 보이는 경우가 발생함.
- 이러한 경우를 맞추기 위해 box-sizing 속성이 등장.
- 기본속성은 conent-box

### margin 겹침현상
mdn에서는 다음과 같이 설명하고 있다.
```
블록의 top 및 bottom 마진은 때로는 (결합되는 마진 중 크기가) 가장 큰 한 마진으로 결합(combine, 상쇄(collapsed))됩니다, 마진 상쇄(margin collapsing)로 알려진 행동
```
- 마진 상쇄는 어떤 두 개 이상 블록 요소의 상하 마진이 겹칠 때 어느 한 쪽의 값만 적용하는 브라우저 나름의 렌더링 규칙 정도로 이해하면 될 것 같다.

### 마진겹침현상의 조건
- 인접하는 Block요소끼리만 일어난다
- 상하단만 병합현상이 일어난다(좌우의 여백의 병합은 일어나지 않음).



