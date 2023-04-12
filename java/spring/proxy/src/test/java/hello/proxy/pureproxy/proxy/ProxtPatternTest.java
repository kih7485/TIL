package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.CacheProxy;
import hello.proxy.pureproxy.proxy.code.ProxtPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxtPatternTest {

    @Test
    void noProxyTest(){
        RealSubject realSubject = new RealSubject();
        ProxtPatternClient client = new ProxtPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();

    }

    @Test
    void cacheProxyTest(){
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        ProxtPatternClient client = new ProxtPatternClient(cacheProxy);
        client.execute();
        client.execute();
        client.execute();
    }
}
