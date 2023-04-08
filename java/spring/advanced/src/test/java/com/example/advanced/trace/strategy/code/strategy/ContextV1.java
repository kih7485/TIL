package com.example.advanced.trace.strategy.code.strategy;

import com.example.advanced.trace.strategy.ContextV1Test;
import com.example.advanced.trace.strategy.code.StrategyLogic1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/*
* 필드에 전략을 보관하는 방식
* */
@Slf4j
public class ContextV1 {
    private Strategy strategy;

    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute(){
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        strategy.call(); //위윔

        //비즈니스 로직 종료

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

}
