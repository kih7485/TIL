package kih.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class HelloRepositoryTest {

    @Autowired JdbcTemplate jdbcTemplate;
    @Autowired HelloRepository helloRepository;

    @Test
    void findHelloFailed(){
        Assertions.assertThat(helloRepository.findHello("inhan")).isNull();
    }

    @Test
    void increaseCount(){
        helloRepository.increaseCount("inhan");
        Assertions.assertThat(helloRepository.countOf("inhan")).isEqualTo(1);


        helloRepository.increaseCount("inhan");
        Assertions.assertThat(helloRepository.countOf("inhan")).isEqualTo(2);
    }
}
