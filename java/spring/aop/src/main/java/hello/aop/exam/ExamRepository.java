package hello.aop.exam;

import hello.aop.exam.annotation.Retry;
import hello.aop.exam.annotation.Trace;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository {

    private static int SEQ = 0;

    /**
     * 5번에 1번 실패하는 요청
     * */
    @Trace
    @Retry
    public String save(String itemId){
        SEQ++;
        if(SEQ % 5 == 0){
            throw new IllegalStateException("예외 발생");
        }
        return "ok";
    }
}
