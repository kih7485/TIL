package hello.proxy.pureproxy.concretproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy extends ConcretLogic{
    private ConcretLogic concretLogic;

    public TimeProxy(ConcretLogic concretLogic) {
        this.concretLogic = concretLogic;
    }

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");
        long start = System.currentTimeMillis();
        String result = concretLogic.operation();
        long end = System.currentTimeMillis();
        System.out.println("TimeProxy 종료, 실행시간: " + (end - start));
        return result;
    };
}
