package kuke.board.view.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kuke.board.view.entity.ArticleViewCount;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleViewCountBackUpRepositoryTest {
    @Autowired
    ArticleViewCountBackUpRepository articleViewCountBackUpRepository;
    @PersistenceContext
    EntityManager em;


    @Test
    @Transactional
    void updateViewCountTest(){
        //given
        articleViewCountBackUpRepository.save(
                ArticleViewCount.init(1L, 0L)
        );

        em.flush();
        em.clear();

        int i1 = articleViewCountBackUpRepository.updateViewCount(1L, 100L);
        int i2 = articleViewCountBackUpRepository.updateViewCount(1L, 300L);
        int i3 = articleViewCountBackUpRepository.updateViewCount(1L, 200L);

        assertThat(i1).isEqualTo(1);
        assertThat(i2).isEqualTo(1);
        assertThat(i3).isEqualTo(0);
    }
}