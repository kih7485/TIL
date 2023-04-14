package hello.proxy.pureproxy.concretproxy;

import hello.proxy.pureproxy.concretproxy.code.ConcretClient;
import hello.proxy.pureproxy.concretproxy.code.ConcretLogic;
import hello.proxy.pureproxy.concretproxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

public class ConcretProxyTest {

    @Test
    void noProxy(){
        ConcretLogic concretLogic = new ConcretLogic();
        ConcretClient client = new ConcretClient(concretLogic);
        client.execute();
    }

    @Test
    void addProxy(){
        ConcretLogic concretLogic = new ConcretLogic();
        TimeProxy timeProxy = new TimeProxy(concretLogic);
        ConcretClient client = new ConcretClient(timeProxy);
        client.execute();
    }
}
