package hello.springtx.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class RollbackTest {

    @Autowired
    RollbackService service;

    @Test
    void runtimeException(){
        Assertions.assertThatThrownBy(() -> service.runtimeException())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void checkedException(){
        Assertions.assertThatThrownBy(() -> service.checkedException())
                .isInstanceOf(Myexception.class);
    }


    @Test
    void rollbackForException(){
        Assertions.assertThatThrownBy(() -> service.rollbackFor())
                .isInstanceOf(Myexception.class);
    }



    @TestConfiguration
    static class RollbackTestConfig{
        @Bean
        RollbackService rollbackService(){
            return new RollbackService();
        }
    }

    @Slf4j
    static class RollbackService{

        //런타임 예외: 롤백
        @Transactional
        public void runtimeException(){
            log.info("call runtimeException");
            throw new RuntimeException();
        }

        //체크예외: 커밋
        @Transactional
        public void checkedException() throws Myexception {
            log.info("call checkedException");
            throw new Myexception();
        }

        //체크예외 rollbackFor 지정: 롤백
        @Transactional(rollbackFor = Myexception.class)
        public void rollbackFor() throws Myexception {
            log.info("call rollbackFor");
            throw new Myexception();
        }
    }

    static class Myexception extends Exception{

    }
}
