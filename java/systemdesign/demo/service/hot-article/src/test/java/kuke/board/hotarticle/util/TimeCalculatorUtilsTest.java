package kuke.board.hotarticle.util;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class TimeCalculatorUtilsTest {

    @Test
    void test(){
        Duration duration = TimeCalculatorUtils.calculateDurationToMidnight();
        System.out.println("duration = " + duration.getSeconds()/60);
    }
}