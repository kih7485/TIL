### 상황에 따라 달라지는 this

#### 전역 공간에서의 this
전역 공간에서 this는 전역 객체를 가리킨다. 
<br/>
브라우저에서 전역객체는 window 이고 Node.js에서는 global이다.


#### 함수 vs 메서드
함수는 그 자체로 독립적인 기능을 수행하는 반며느 메서드는 자신을 호출한 대상 객체에 관한 동작을 수행.
```js
var obj1 = {
    outer: function(){
        console.log(this); //obj1
        var innerFunc = function(){
            console.log(this); //Window
        }
        innerFunc();
        var obj2 = {
            innerMethos: innerFunc
        };
        obj2.innerMethod(); // obj2
    }
}
obj1.outer();
```