package hello.aop.order.member;

import hello.aop.order.member.annotation.ClassAOP;
import hello.aop.order.member.annotation.MethodAop;
import org.springframework.stereotype.Component;

@ClassAOP
@Component
public class MemberServiceImpl implements MemberService{
    @Override
    @MethodAop("test value")
    public String hello(String param) {
        return "ok";
    }

    public String internal(String param){
        return "ok";
    }
}
