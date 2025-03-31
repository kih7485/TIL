package kuke.board.article.repository;

import kuke.board.article.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ArticleRespositoryTest {
    @Autowired
    ArticleRespository articleRespository;

    @Test
    void testFindAll(){
        List<Article> articles = articleRespository.findAll(1L, 1499970L, 30L);
        log.info("article size={}", articles.size());
        for (Article article: articles) {
            log.info("article={}" , article);
        }
    }

    @Test
    void countTest(){
        Long count = articleRespository.count(1L, 10000L);
        log.info("count={}", count);
    }

    @Test
    void findInfiniteScrollTest(){
        List<Article> articles = articleRespository.findAllInfiniteScroll(1L, 30L);
        for (Article article: articles
             ) {
            System.out.println("article = " + article);
        }

        Long lastArticleId = articles.getLast().getArticleId();
        List<Article> articles2 = articleRespository.findAllInfiniteScroll(1L, 30L, lastArticleId);

        for (Article article: articles2
        ) {
            System.out.println("article = " + article);
        }
    }
}