package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.service.ConcretService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CjlibTest {

    @Test
    void cglibTest(){
        ConcretService target = new ConcretService();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ConcretService.class);
        enhancer.setCallback(new TimeMethodInterceptor(target));
        ConcretService proxy = (ConcretService) enhancer.create();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();
    }
}
