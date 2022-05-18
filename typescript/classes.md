### 클래스 정의
- 타입스크립트는 클래스 몸체에 프로퍼티를 먼저 선언하여야 한다
```ts
// person.ts
class Person {
  // 클래스 프로퍼티를 사전 선언하여야 한다
  name: string;

  constructor(name: string) {
    // 클래스 프로퍼티수에 값을 할당
    this.name = name;
  }

  walk() {
    console.log(`${this.name} is walking.`);
  }
}

const person = new Person('Lee');
person.walk(); // Lee is walking
```

### 접근 제한자
- 타입스크립트는 public 외에도 private, protected의 접근제한자를 지원한다.
- 접근제한자를 생략하면 기본적으로  public으로 선언된다.


## static
- 정적 메소드는 클래스의 인스턴스가 아닌 클래스 이름으로 호출한다. 따라서 클래스의 인스턴스를 생성하지 않아도 호출할 수 있다.
- 프로퍼티에도 static 키워드를 사용할 수 있다.
  
```ts
class Foo {
  static instanceCounter = 0;
  constructor(prop) {
    Foo.instanceCounter++;
    this.prop = prop;
  }
  
  static staticMethod() {
    /*
    정적 메소드는 this를 사용할 수 없다.
    정적 메소드 내부에서 this는 클래스의 인스턴스가 아닌 클래스 자신을 가리킨다.
    */
    return 'staticMethod';
  }

  prototypeMethod() {
    return this.prop;
  }
}

// 정적 메소드는 클래스 이름으로 호출한다.
console.log(Foo.staticMethod());

const foo = new Foo(123);
// 정적 메소드는 인스턴스로 호출할 수 없다.
console.log(foo.staticMethod()); // Uncaught TypeError: foo.staticMethod is not a function

var foo1 = new Foo();
var foo2 = new Foo();

console.log(Foo.instanceCounter);  // 2
console.log(foo2.instanceCounter); // error TS2339: Property 'instanceCounter' does not exist on type 'Foo'.

```