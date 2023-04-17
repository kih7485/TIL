package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0(){
        Hello target = new Hello();

        log.info("start");
        String result1 = target.callA();
        log.info("result = {}", result1);


        log.info("start");
        String result2 = target.callB();
        log.info("result = {}", result2);
    }

    @Test
    void reflection1() throws ClassNotFoundException {

        //클래스 정보
        Class hello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        //callA 메서드 정보

    }


    @Slf4j
    static class Hello{
        public String callA(){
            log.info("callA");
            return "A";
        }
        public String callB(){
            log.info("callB");
            return "B";
        }
    }
}
