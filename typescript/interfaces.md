### interface
- 인터페이스는 오롯이 한가지 용도만을 가짐.
- 인터페이스는 타입스크립트에게 오브젝트 모양을 설명해 주는 하나의 목적으로만 사용 가능.

### 인터페이스와 타입의 차이
인터페이스와 타입의 지정은 다음과 같이 한다.
```ts
interface IStudent {
    id: number;
    name: string;
}
type TStudent = {
    id: number;
    name: string;
};
const interfaceStudent: IStudent = {
    id: 0,
    name: 'name',
};
const typeStudent: TStudent = {
    id: 0,
    name: 'name',
};
```

type은 & 연산자, interface는 extends 를 이용해 확장한다
```ts
interface IStudent2 extends IStudent {
    age: number;
}
type TStudent2 = TStudent & {
    age: number;
};
```

interface는 동일한 이름으로 재정의가 가능하다.
```ts
interface IStudent {
    id: number;
    name: string;
}
interface IStudent {
    age: number;
}
```
