package hello.aop.proxyvs;

import hello.aop.order.member.MemberService;
import hello.aop.order.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ProxyCastingTest {

    @Test
    void jdkProxy(){
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); //JDK 동적 프록시

        //프록시를 인터페이스로 캐스팅
        MemberService memberServiceProxy =(MemberService) proxyFactory.getProxy();

        //JDK 동적 프록시 구현 클래스 캐스팅 시도 실패, ClassCastingException 발생
        Assertions.assertThrows(ClassCastException.class, () ->
        {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        });

    }

    @Test
    void cglibProxy(){
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); //CGLIB 프록시

        //프록시를 인터페이스로 캐스팅
        MemberService memberServiceProxy =(MemberService) proxyFactory.getProxy();

        //CGLIB 동적 프록시 구현 클래스 캐스팅 시도 성공
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;

    }
}
