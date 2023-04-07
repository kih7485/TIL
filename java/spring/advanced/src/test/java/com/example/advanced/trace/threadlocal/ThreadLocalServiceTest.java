package com.example.advanced.trace.threadlocal;

import com.example.Util;
import com.example.advanced.trace.template.code.AbstractTemplate;
import com.example.advanced.trace.template.code.SubClassLogic1;
import com.example.advanced.trace.template.code.SubClassLogic2;
import com.example.advanced.trace.threadlocal.code.FieldService;
import com.example.advanced.trace.threadlocal.code.ThreadLocalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ThreadLocalServiceTest {
    private ThreadLocalService Service = new ThreadLocalService();

    @Test
    void test(){
        log.info("main start");
        Runnable userA = () -> {
            Service.logic("userA");
        };

        Runnable userB = () -> {
            Service.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-a");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-b");

        threadA.start();
        //Util.sleep(2000); //동시성 문제 발생X
        Util.sleep(100); //동시성 문제 발생O
        threadB.start();

        Util.sleep(3000);
        log.info("main exit");
    }

    /*
    * 템플릿 메서드 패턴 적용
    * */
    @Test
    void templateMethodV1(){
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();

        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();
    }
}
