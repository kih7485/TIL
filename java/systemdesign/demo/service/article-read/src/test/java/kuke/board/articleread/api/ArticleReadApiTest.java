package kuke.board.articleread.api;

import kuke.board.articleread.service.event.response.ArticleReadPageResponse;
import kuke.board.articleread.service.event.response.ArticleReadResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class ArticleReadApiTest {
    RestClient articleReadRestClient = RestClient.create("http://localhost:9005");
    RestClient articleRestClient = RestClient.create("http://localhost:9000");

    @Test
    void readTest(){
        ArticleReadResponse body = articleReadRestClient.get()
                .uri("/v1/articles/{articleId}", 163933250930802689L)
                .retrieve()
                .body(ArticleReadResponse.class);

        System.out.println("response = " + body);
    }

    @Test
    void readAllTest(){
        ArticleReadPageResponse response1 = articleReadRestClient.get()
                .uri("/v1/articles?boardId=%s&page=%s&pageSize=%s".formatted(1L, 1L, 5))
                .retrieve()
                .body(ArticleReadPageResponse.class);

        System.out.println("response1.getArticleCount() = " + response1.getArticleCount());
        for (ArticleReadResponse article : response1.getArticles()) {
            System.out.println("article.getArticleId() = " + article.getArticleId());
        }

        ArticleReadPageResponse response2 = articleRestClient.get()
                .uri("/v1/articles?boardId=%s&page=%s&pageSize=%s".formatted(1L, 1L, 5))
                .retrieve()
                .body(ArticleReadPageResponse.class);

        System.out.println("response2.getArticleCount() = " + response2.getArticleCount());
        for (ArticleReadResponse article : response2.getArticles()) {
            System.out.println("article.getArticleId() = " + article.getArticleId());
        }
    }

    @Test
    void readAllInfiniteScroll(){
        List<ArticleReadResponse> response1 = articleReadRestClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=%s&pageSize=%s".formatted(1L, 5))
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleReadResponse>>() {
                });

        for (ArticleReadResponse articleReadResponse : response1) {
            System.out.println("articleReadResponse.getArticleId() = " + articleReadResponse.getArticleId());
        }

        List<ArticleReadResponse> response2 = articleRestClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=%s&pageSize=%s".formatted(1L, 5))
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleReadResponse>>() {
                });

        for (ArticleReadResponse articleReadResponse : response2) {
            System.out.println("articleReadResponse.getArticleId() = " + articleReadResponse.getArticleId());
        }
    }
}
