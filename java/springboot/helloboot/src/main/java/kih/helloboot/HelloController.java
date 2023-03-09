package kih.helloboot;

import java.util.Objects;

public class HelloController {
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    public String hello(String name){
        SimpleHelloService helloService = new SimpleHelloService();

        return helloService.seyHello(Objects.requireNonNull(name));
    }
}
