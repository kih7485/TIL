package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
public class AspectV3 {

    //hello.aop.order 패키지 하위에 있는 모든 메서드를 다 적용하겠다.
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){}

    //클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService(){}

    @Around("allOrder()")
    @Order(1)
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); //joinpoint 시그니쳐
        return joinPoint.proceed();
    }

    //hello.aop.order 패키지와 하위 패키지 서비스이면서 클래스 이름 패턴이 *Service
    @Around("allOrder() && allService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try{
            log.info("[트랜잭션 시작] {}",joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}",joinPoint.getSignature());

            return result;
        }catch (Exception e){
            log.info("[트랜잭션 롤백] {}",joinPoint.getSignature());
            throw e;
        }finally {
            log.info("[리소스 릴리즈] {}",joinPoint.getSignature());
        }
    }
}
