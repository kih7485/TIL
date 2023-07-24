package hello.aop.order.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

public class Pointcuts {

    //hello.aop.order 패키지 하위에 있는 모든 메서드를 다 적용하겠다.
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder(){}

    //클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}

}
