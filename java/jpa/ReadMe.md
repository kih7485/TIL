#### 읽기 쉽게 작성하기

- 나쁜 코드
```java
//상수를 바로 입력하면 유추가 불가능함.
setTokenTimeout(86400000);
```
- 좋은 코드
```java
//상수로 선언 후 입력한다.
private static final int MILISECOND_IN_A_DAY = 24*60*60*1000
setTokenTimeout(MILISECOND_IN_A_DAY);
```

#### 중복된 의미를 갖는 단어 사용하지 않기
---- 
#### 일관성 있는 클래스 멤버 순서
----  
#### 코드가 스스로 말하게 하라
 - 굳이 주석을 달지 않아도 코드 자체로 이해가 가능하게 작성할 수 있도록 한다.

---

#### 주석 원칙
 - 코드로 표현할 수 없을때만 주석을 처리한다.
 - 불필요한 주석은 삭제한다.
 - 주석을 코드로 표현할 수 있다면 코드를 변경한다.

일반 원칙 
 - 의미있는 이름 사용 
 - 표준 규약 따르기 
-  KISS 원칙 
 - Keep It Short and Simple 
   -  Keep It Small and Simple 
     - Keep It Simple and Straight 
   - Keep It Simple, Stupid

- POLA 원칙 
  - Principle of Least Astonishment 
  - Principle of Least Surprise 
  - 일관성 
  - 유사한 방식으로 해결하기

클래스
 - 명사형 사용
 - 내부를 감추고 범위를 최소화
 - 의존성 주입(DI, Dependency Injection) 사용
 - 느슨한 결합성(loosely-coupled)과 강한 밀집성(strong cohesion)
 - 수퍼 클래스는 파생 클래스에 대해 알지 못해야 한다
 - 불변성(immutability)
  
 함수/메서드
 -  동사형 사용
 -  작아야 한다
 -  단 하나의 일만 수행해야 한다
 -  단일 책임의 원칙(SRP, Single Responsibility Principle)을 따른다
 -  기존의 것을 활용한다
 -  DRY(Don’t Repeat Yourself)을 따른다
 -  부작용(side effects)를 갖지 않도록 한다
 -  유사한 함수는 가까이 둔다
 -  같은 개념을 다른 단어로 정의하지 않는다
 -  find, fetch, get, lookup, search
 -  가시성이 높을 수록 짧은 함수명을 사용한다. 

변수
-  변수를 사용하는 곳 가까이 둔다.
-  범위가 넓을 수록 긴 변수명을 사용한다.

불변성 객체(immutable object)
- 이점
- 스레드 안정적(thread safe)
- 동기화 접근(synchronized access)을 피할 수 있음
- 객체를 함께 사용할 때 유용함
- 신뢰할 수 없는 코드에도 안전하게 넘겨줄 수 있음
- 방법
- 상태를 변경하는 메서드를 제공하지 않는다
- final 클래스로 정의한다
- private final 필드로 정의한다

재사용성 상속 vs 포함
- 상속(inheritance) 재사용성(reusability)
    - 상속성이 항상 코드를 재사용하는 가장 좋은 방법은 아니다
    -  깨지기 쉬운 코드를 만들 수 있다
- 구현 상속성(implmentation inheritance)에서 서브 클래스가 수퍼 클래스의 구현 세부 사항에 의존할 때 상속성은 캡슐화를 위반한다
- 상속성의 단점
    - 좋은 상속 계층성을 만드는 일은 어렵다
    - 코드를 이해하기 어렵게 만들 수 있다
    - 계층성은 유연성을 제공하지 않는다
    - 하나의 수퍼 클래스에서만 상속할 수 있다
    - 클래스가 상속하는 수퍼 클래스를 실행 시에 변경할 수 없다
- 포함(composition)의 장점
  - 커다란 계층성 보다 이해하기 쉽다
  - 실행 시에도 컴포넌트를 대체할 수 있다
  - 컴포넌트를 분리하여 테스트 할 수 있다

@FuntionalInterface 사용
- 함수형 인터페이스(functional interface)
- SAM(Single Abstract Method) 타입
- 람다 표현식(lambda expression)으로 인스턴스 생성
- 자바 내장 함수형 인터페이스
- java.lang.Runnable
- java.util.Comparator
- java.util.concurrent.Callable
- java.io.FileFilter
  
  
Objects 메서드
- Objects.requireNonNull(obj)
- Objects.requireNonNull(obj, message)
- Objects.requireNonNull(obj, messageSupplier)
- Objects.requireNonNullElse(obj, defaultObj)
- Objects.requireNonNullElseGet(obj, supplier)
- Objects.checkIndex(index, length)
- Objects.checkFromIndexSize(fromIndex, size, length)
- Objects.checkFromToIndex(fromIndex, toIndex, length)

객체 지향 설계 원칙
- 잘못된 설계 낌새
- Single-Responsibility Principle (SRP)
- Open/Closed Principle (OCP)
- Liskov Substitution Principle (LSP)
- Dependency-Inversion Principle (DIP)
- Interface Segregation Principle (ISP)
- 나쁜 설계의 징후
    - 변경하기 어렵다
    - 깨지기 쉽다
    - 재사용하기 어렵다
    - 과도하게 설계되어 있다
    - 불필요한 반복이 많다
    - 표현이 무질서하다

SOLID 원칙
- Single-Responsibility Principle (SRP)
    -  단일 책임의 원칙
        - 클래스는 하나의 책임만 갖는다
        - 클래스가 여러 책임을 갖게 되면 그 클래스는 각 책임마다 변경되는 이유가 발생하기 때문에
클래스가 한 개의 이유로만 변경되려면 클래스는 한 개의 책임만을 가져야한다
        - 이러한 이유로 이 원칙은 다른 말로 "클래스를 변경하는 이유는 단 한 개여야 한다."고도 표현
된다
- Open/Closed Principle (OCP)
  - 개방 폐쇄 원칙
    - 소프트웨어 모듈은 확장에 대해 열려 있어야 하고, 변경에 대해서는 닫혀 있어야 한다
    - 열린 확장(open for extension)
    - 애플리케이션의 요구가 변경될 때 요구를 만족시키는 새로운 행위로 모듈을 확장할 수 있다
    - 닫힌 변경(close for modification)
    - 모듈의 행위를 확장해도 다른 코드를 변경시키지 않아야 한다
- Liskov Substitution Principle (LSP)
- Interface Segregation Principle (ISP)
- Dependency-Inversion Principle (DIP)




























