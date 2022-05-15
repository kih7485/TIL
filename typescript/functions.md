### call signature
- 변수나 함수 등에 type을 지정하는 형태
  
```typescript 
type DescribableFunction = {
  description: string;
  (someArg: number): boolean;
};
function doSomething(fn: DescribableFunction) {
  console.log(fn.description + " returned " + fn(6));
}
```
- 위 코드에서 DescribableFunction타입으로 선언된 fn은 자동추론이 가능하고 
type이 변함에 따라 fn도 같이 변하게 되므로 재사용성의 효율이 높아진다.
- react, vue 등에서 아주 많이 쓰임.
  
### Generic
- 메서드 매개변수의 구체적 타입을 기재하지 않고 다양한 타입을 처리할 수 있게 함.
- 함수, 인터페이스, 클래스 등의 재사용성을 높일 수 있다.
  