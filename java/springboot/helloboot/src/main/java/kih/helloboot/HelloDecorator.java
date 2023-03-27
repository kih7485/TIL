package kih.helloboot;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class HelloDecorator implements HelloService{
    private final HelloService helloService;

    HelloDecorator(HelloService helloService){
        this.helloService = helloService;
    }
    @Override
    public String seyHello(String name) {
        return "*" + helloService.seyHello(name) + "*";
    }

    @Override
    public int countOf(String name) {
        return helloService.countOf(name);
    }
}
