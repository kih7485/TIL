package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CheckedTest {

    @Test
    void checked_catch(){
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checked_throw() throws MycheckedException {
        Service service = new Service();

        Assertions.assertThatThrownBy(() -> service.callThrow()).isInstanceOf(MycheckedException.class);
    }

    /**
     * Exception을 상속받은 예외는 체크 예외가 된다.
     * */
    static class MycheckedException extends Exception{
        public MycheckedException(String message){
            super(message);
        }
    }

    static class Service{
        Repository repository= new Repository();

        /**
         * 예외를 잡아서 처
         * */
        public void callCatch(){
            try {
                repository.call();
            } catch (MycheckedException e) {
                log.info("ex={}", e.getMessage(), e);
            }
        }

        public void callThrow() throws MycheckedException{
            repository.call();
        }
    }

    static class Repository{
        public void call() throws MycheckedException {
            throw new MycheckedException("Ex");
        }
    }
}