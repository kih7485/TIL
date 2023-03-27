package kih.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.stream.IntStream;

@HelloBootTest
public class HelloServiceCountTest {

    @Autowired HelloService helloService;
    @Autowired HelloRepository helloRepository;

    @Test
    void sayHelloIncreaseCount(){
        IntStream.rangeClosed(1,50).forEach(count -> {
            helloService.seyHello("inhan");
            Assertions.assertThat(helloRepository.countOf("inhan")).isEqualTo(count);
        });

    }
}
