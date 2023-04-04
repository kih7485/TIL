package com.example.advanced.trace.threadlocal;

import com.example.Util;
import com.example.advanced.trace.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class FieldServiceTest {
    private FieldService fieldService = new FieldService();

    @Test
    void test(){
        log.info("main start");
        Runnable userA = () -> {
            fieldService.logic("userA");
        };

        Runnable userB = () -> {
            fieldService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-a");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-b");

        threadA.start();
//        Util.sleep(2000); //동시성 문제 발생X
        Util.sleep(100); //동시성 문제 발생O
        threadB.start();

        Util.sleep(3000);
        log.info("main exit");
    }
}
