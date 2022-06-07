### px과 rem의 차이
- 사용자가 브라우저의 글꼴 크기를 키웠을 떄 px는 고정이고 rem은 사이즈가 변함.

### em과 rem의 차지
- em은 부모요소에게 영향을rem은 최상단 부모요소(html)에게 영향을 받음.

### font-familly
- 서체를 지정하는 속성

### font-weight
- 폰트의 두께

### line-height
- 행과 행 사이의 간격을 지정(기본값 1.2)

### font
- 폰트와 관련된 여러 속성을 한번에 줌
```css
  /* 글씨 크기 12픽셀, 줄 높이 14픽셀, 글꼴 sans-serif */
p { font: 12px/14px sans-serif }

/* 글씨 크기 부모 또는 기본값(부모가 없을 경우)의 80%, 글꼴 sans-serif */
p { font: 80% sans-serif }

/* 글씨 굵기 굵게, 스타일 기울이기, 글씨 크기 크게, 글꼴 serif */
p { font: bold italic large serif }

/* 창의 상태표시줄과 같은 글꼴 사용 */
p { font: status-bar }
```

### @font-face
- 웹페이지의 텍스트에 온라인폰트(online fonts)를 적용할 수 있다
```css
@font-face {
  font-family: <a-remote-font-name>;/*font 속성에서 폰트명(font face)으로 지정될 이름을 설정한다.*/
  src: <source> [,<source>]*;/*원격 폰트(remote font) 파일의 위치를 나타내는 URL 값을 지정하거나, 사용자 컴퓨터에 설치된 폰트명을 local("Font Name")형식으로 지정하는 속성이다.
*/
  [font-weight: <weight>];
  [font-style: <style>];
}
```