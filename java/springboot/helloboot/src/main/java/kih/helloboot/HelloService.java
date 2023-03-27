package kih.helloboot;

public interface HelloService {
    String seyHello(String name);

    default int countOf(String name){
        return 0;
    }
}
