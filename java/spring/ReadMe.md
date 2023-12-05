### @Configuration


스프링 부트?
- 스프링 프레임워크를 잘 쓰게 도와주는 도구의 모음
- 서블릿 컨테이너와 관련된 모든 번거로운 작업을 감춰줌
- 스프링과 각종 기술의 주요 인스스럭쳐 빈을 자동구성을 이용해서 자동으로 등록해줌.
- 외부 설정, 커스텀 빈 등록을 통해서 유연하게 확장 가능.


JDBC 구성
- 커넥션 설정 기본은 HikariCP


공부해야 할 자동구성정보
- PersistenceExceptionTranslationAutoConfiguration
- DataSourceAutoConfiguration
- DataSourceTransactionManagerAutoConfiguration
- JdbcTemplateAutoConfiguration
- TransactionAutoConfiguration

AOP 용어정리
- 조인포인트(JoinPoint): 메소드 실행 시점
  - 어드바이스 적용될 수 있는 위치, 메소드 실, 생성자 호출, 필드 값 접근, static 메서드 접근 같 프로그램 실행중 지점.
  - 조인포인트는 추상적 개념이다. 
  - 스프 AOP는 프록시 방식을 사용하므로, 조인포인트 항상 메서드 실행 지점으로 제한한다.
- 포인트컷(PointCut): 어떤 메소드를 사용할 것인지
  - 조인포인트 중에 어드바이스 적용될 위치 선별하는 기능
  - 주로 AspectJ 표현식을 사용한다.
  - 프록시를 사용하 스프링 AOP 메서드 실행 지점만 포인트컷으 선별 가
- 타겟(Target): 어드바이스 적용 대상
  - 주로 부가기능을 부여할 대상
  - 스프링 AOP는 프록시 기반의 AOP를 구현하므로, 타겟은 항상 빈이어야 한다.
- 어드바이스(Advice): 메소드 실행 전, 후, 예외 발생 시점에 실행할 코드
  - 부가 기능
  - 어드바이스는 그 자체로는 무기능한데, 어드바이스를 어디에 적용할지, 언제 적용할지, 어떻게 적용할지 정의해야 한다.
- 애스펙트(Aspect)
  - 어드바이스와 포인트컷의 조합
  - 여러 어드바이스 포인트컷이 함께 존재
- 어드바이저
  - 하나의 어드바이스와 하나의 포인트컷으로 구성
  - 스프링 AOP에서는 어드바이저를 빈으로 등록해서 사용한다.
- 위빙
  - 포인트컷으로 결정한 타겟의 조인포인트에 부가기능을 삽입하는 과정
  - 위빙을 통해 핵심기능의 코드에 영향을 주지않고 부가기능을 추가할 수 있다.
  - AOP 적용을 위해 애스펙트를 객체에 연결한 상태

포인트컷 지시자의 종류
 - excution: 메서드 실행 조인포인트를 매칭, 스프링AOP에서 가장 많이 사용하고 기능도 복잡.
 - within: 특정 타입 내의 조인포인트를 매칭한다.
 - args: 인자가 주어진 타입의 인스턴스 조인포인트

 외부설정 우선순위
 1. 설정데이터(application.properties)
 2. OS 환경변수
 3. 자바 시스템 속성
 4. 커맨드 라인 옵션 인수(args)
 5. @TestPropertySource (테스트에서 사용)

 설정데이터 우선순위
 1. jar 내부 application.properties
 2. jar 내부 프로필 적용 파일 application-{profile}.properties
 3. jar 외부 application.properties
 4. jar 외부 프로필 적용 파일 application-{profile}.properties

spring actuator

기본으로 제공하는 애플리케이션 정보
- java: 자바 런타임 정보(기본 비활성)
- os: OS 정보
- env: Environment에서 info. 로 시작하는 정보(기본 비활성)
- build: 빌드정보/META-INF/build-info.properties 파일이 필요
- git: git 정보(기본 비활성화)

#### 매트릭

jvm 매트릭
- jvm 관련 매트릭 제공. jvm. 으로 시작한다
- 메모리 및 버퍼 풀 세부정보
- 가비지 수집 관련 통계
- 스레드 활용
- 로드 및 언로드 된 클래스 수
- jvm 버전정보
- JIT 컴파일 시간

시스테 메트릭
- CPU 지표
- 파일 디스크립터 메트릭
- 가동시간 메트릭
- 사용 가능한 디스크 공간

스프링 MVC 메트릭(http.server.request)
- uri: 요청 uri
- method: GET, POST 같은 메서드
- outcome: 상태코드를 그룹으로 모아서 확인

톰캣 메트릭
```yml
server:
  tomcat:
    mbeanregistry:
      enabled: true
```
- 톰캣의 최대 쓰레드, 사용 쓰레드 수를 포함한 다양한 메트릭을 확인할 수 있다.

사용자 정의 메트릭
- 사용자가 직접 메트릭 정의
- 입고 수 , 출고 수, 반품 수 등을 메트릭으로 표시.

메트릭 예제

- OrderConfig
```java
package hello.order.v4;

import hello.order.OrderService;
import hello.order.v3.OrderServiceImplV3;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfigV4 {

    @Bean
    OrderService orderService(){
        return new OrderServiceImplV4();
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry){
        return new TimedAspect(registry);
    }
}

```
-OrderService
```java
package hello.order.v4;

import hello.order.OrderService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Timed("my.order")
@Slf4j
public class OrderServiceImplV4 implements OrderService {
    private AtomicInteger stock = new AtomicInteger(100);
    @Override
    public void order() {
        log.info("주문");
        stock.decrementAndGet();
        sleep(500);
    }

    private void sleep(int l) {
        try {
            Thread.sleep(l + new Random().nextInt(200));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cancel() {
        log.info("취소");
        stock.incrementAndGet();
        sleep(200);
    }

    @Override
    public AtomicInteger getStock() {
        return stock;
    }
}

```

마이크로미터 핵심기능
- Counter, Gauge, Timer , Tags