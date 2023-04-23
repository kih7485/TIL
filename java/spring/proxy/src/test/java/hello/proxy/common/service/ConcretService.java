package hello.proxy.common.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcretService {
    public void call(){
        log.info("ConcretService 호출");
    }
}
