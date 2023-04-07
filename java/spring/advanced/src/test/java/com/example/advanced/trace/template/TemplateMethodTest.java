package com.example.advanced.trace.template;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

@Slf4j
public class TemplateMethodTest {

    @Test
    void theplateMethodV0(){
        logic1();
    }

    @TestTemplate
    private void logic1(){
        long startTime = System.currentTimeMillis();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
