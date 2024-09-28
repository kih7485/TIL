package tobyspring.hellospring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class SortTest {
    Sort sort;
    @BeforeEach
    void init(){
        sort = new Sort();
    }

    @Test
    void sort(){
        //실행
        List<String> list = sort.sortByLength(Arrays.asList("aa", "b"));
        //검증
        Assertions.assertThat(list).isEqualTo(List.of("b","aa"));
    }

    @Test
    void sort3Items(){

        //실행
        List<String> list = sort.sortByLength(Arrays.asList("aa", "ccc","b","bb"));
        //검증
        Assertions.assertThat(list).isEqualTo(List.of("b","aa","bb","ccc"));
    }
}