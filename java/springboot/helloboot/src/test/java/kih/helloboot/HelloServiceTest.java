package kih.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloServiceTest {

    @Test
    void simpleHelloService(){
        SimpleHelloService helloService = new SimpleHelloService();

        String ret = helloService.seyHello("inhan");

        Assertions.assertThat(ret).isEqualTo("hello inhan");
    }

    @Test
    void helloDecorator(){
        HelloDecorator decorator = new HelloDecorator(name -> name);
        String test = decorator.seyHello("Test");

        Assertions.assertThat(test).isEqualTo("*Test*");
    }
}
