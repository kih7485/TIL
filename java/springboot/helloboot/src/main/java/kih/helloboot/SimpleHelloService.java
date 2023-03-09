package kih.helloboot;

public class SimpleHelloService implements HelloService {

    @Override
    public String seyHello(String name){
        return "hello "+name;
    }
}
