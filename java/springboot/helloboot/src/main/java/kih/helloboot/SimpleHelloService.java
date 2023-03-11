package kih.helloboot;

import org.springframework.stereotype.Component;

@Component
public class SimpleHelloService implements HelloService {

    @Override
    public String seyHello(String name){
        return "hello "+name;
    }
}
