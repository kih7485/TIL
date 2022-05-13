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