package hello.proxy.pureproxy.concretproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcretLogic {
    public String operation(){
        log.info("ConcretLogic 실행");
        return "data";
    }
}
