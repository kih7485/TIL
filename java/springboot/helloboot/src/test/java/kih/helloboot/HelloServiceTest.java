package kih.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloServiceTest {

    @Test
    void simpleHelloService(){
        SimpleHelloService helloService = new SimpleHelloService(helloRepositoryStub);

        String ret = helloService.seyHello("inhan");

        Assertions.assertThat(ret).isEqualTo("hello inhan");
    }

    private static HelloRepository helloRepositoryStub = new HelloRepository() {
            @Override
            public Hello findHello(String name) {
                return null;
            }

            @Override
            public void increaseCount(String name) {

            }
    };

    @Test
    void helloDecorator(){
        HelloDecorator decorator = new HelloDecorator(name -> name);
        String test = decorator.seyHello("Test");

        Assertions.assertThat(test).isEqualTo("*Test*");
    }
}
