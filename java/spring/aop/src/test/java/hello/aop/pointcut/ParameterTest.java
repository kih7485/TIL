package hello.aop.pointcut;

import hello.aop.order.member.MemberService;
import hello.aop.order.member.annotation.ClassAOP;
import hello.aop.order.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {
    @Autowired
    MemberService memberService;


    @Test
    void success(){
        log.info("memberService Porxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect{
        @Pointcut("execution(* hello.aop.order.member..*.*(..))")
        private void allMember(){

        }

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1]{}, arg={}",joinPoint.getSignature(), arg1);
            return joinPoint.proceed();
        }

        @Around("allMember() && args(arg, ..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs2]{}, arg={}",joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        @Before("allMember() && args(arg, ..)")
        public void logArgs3(String arg){
            log.info("[logArgs3], arg={}", arg);
        }

        @Before("allMember() && this(obj)")
        public void thisArgs(JoinPoint joinPoint, MemberService obj){
            log.info("[this]{}, obj={}", joinPoint.getSignature(),obj.getClass());
        }

        @Before("allMember() && target(obj)")
        public void targetArgs(JoinPoint joinPoint, MemberService obj){
            log.info("[target]{}, obj={}", joinPoint.getSignature(),obj.getClass());
        }

        @Before("allMember() && @target(annotaion)")
        public void atTargetArgs(JoinPoint joinPoint, ClassAOP annotaion){
            log.info("[@target]{}, obj={}", joinPoint.getSignature(), annotaion);
        }

        @Before("allMember() && @within(annotaion)")
        public void atWithinArgs(JoinPoint joinPoint, ClassAOP annotaion){
            log.info("[@within]{}, obj={}", joinPoint.getSignature(), annotaion);
        }

        @Before("allMember() && @annotation(annotaion)")
        public void atAnnotationArgs(JoinPoint joinPoint, MethodAop annotaion){
            log.info("[@annotaion]{}, annotaionValue={}", joinPoint.getSignature(), annotaion.value());
        }
    }

}
