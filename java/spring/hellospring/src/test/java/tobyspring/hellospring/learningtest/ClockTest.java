package tobyspring.hellospring.learningtest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ClockTest {

    //Clock을 이용해서 LocalDateTime.now
    @Test
    void clock(){
        Clock clock = Clock.systemDefaultZone();

        LocalDateTime dt1 = LocalDateTime.now(clock);
        LocalDateTime dt2 = LocalDateTime.now(clock);
        Assertions.assertThat(dt2).isAfter(dt1);

    }

    //Clock을 테스트해서 사용할 때 내가 원하는 시간을 지정
    @Test
    void fixedClock(){
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        LocalDateTime dt1 = LocalDateTime.now(clock);
        LocalDateTime dt2 = LocalDateTime.now(clock);
        Assertions.assertThat(dt2).isEqualTo(dt1);
    }
}
