### unknown 타입

```typescript 
let a: unknown;
if(typeof === 'number'){
    let b = a + 1;
}
if(typeof === 'string'){
    let b = a.toUpperCase();
}
```

### void 타입
- void는 아무것도 return하지 않는 함수를 대상으로 함.
- void는 보통 타입스크립트에서 자동추론하기 때문에 굳이 써주지 않아도 됨.
```typescript 
function hello():void{ //타입스크립트가 void형으로 자동추론함.
    console.log('hello');
}
```

### never
- 항상 오류를 출력하거나 리턴 값을 절대로 내보내지 않음을 나타낼 떄 사용

```typescript
function hello():never{
    throw new Error('hello');
}

function hello(name:string|number):{
    if(typeof === 'number'){
        name;
    }
    else if(typeof === 'string'){
        name;
    }else{
        name;
        //name은 string과 number형만 올수 있기 때문에 마지막 조건에서는 never타입으로 처리
    }
}
```

### 타입 연산과 제네릭으로 반복 줄이기
- DRY(dont repeat yourself) 원칙을 타입에도 최대한 적용해야 한다.
- 타입에 이름을 붙여서 반복을 피해야 한다. extends를 사용해서 인터페이스 필드의 반복을 피해야 한다.
- 타입들 간의 매핑을 위해 타입스크립트가 재공한 도구들을 공부하자. keyof, typeof, 인덱싱, 매핑된 타입들이 포함된다.
- 제네릭 타입은 타입을 위한 함수와 같다. 타입을 반복하는 대신 제너릭타입을 사용하여 타입들 간에 매핑을 하는 것이 좋다. 제네릭 타입을 제한하려면 extends를 사용하면 된다.
- 표준라이브러리에 정의된 Pick, Partial, ReturnType 같은 제네릭타입에 익숙해져야 한다.

### 동적 데이터에 인덱스 시그니쳐 사용하기
- 런타임 때까지 객체의 속성을 알 수 없을때만 인덱스 시그니처를 사용하도록 한다.
- 안전한 접근을 위해 인덱스 시그니처의 값 타입에 undefined를 추가하는 것을 고려해야 한다.
- 가능하다면 인터페이스, Record, 매핑된 타입 같은 인덱스 시그니처보다 정확한 타입을 사용하는 것이 좋다.

### number인덱스 시그니처보다는 Array, 튜플, ArrayLike를 사용하기
```ts
"0"===0 
//true
```
  위의 코드 결과처럼 자바스크립트에서 문자열 0 은 숫자 0과 동일한 결과로 출력된다.
- 배열은 객체이므로 키는 수자가 아니라 문자열이다. 인덱스 시그니처로 사용된 number 타입은 버그를 잡기 위한 순수 타입스크립트 코드이다.
- 인덱스 시그니처에 number를 사용하기보다 Array나 튜플, 또는 ArrayLike타입을 사용하는 것이 좋다.
