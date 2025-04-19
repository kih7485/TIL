package kuke.board.article.api;

import kuke.board.article.service.response.ArticlePageResponse;
import kuke.board.article.service.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

public class ArticleApiTest {

    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void createTest(){
        ArticleResponse response = create(new ArticleCreateRequest("hi", "my content", 1L, 1L));
        System.out.println("response = " + response);
    }

    @Test
    void readTest(){
        ArticleResponse response = read(162926650401046528L);
        System.out.println("response = " + response);
    }

    @Test
    void updateTest(){
        update(162926650401046528L);
        ArticleResponse response = read(162926650401046528L);
        System.out.println("response = " + response);
    }

    @Test
    void deleteTest(){
        delete(162926650401046528L);
        ArticleResponse response = read(162926650401046528L);
        System.out.println("response = " + response);
    }

    @Test
    void readAllTest(){
        ArticlePageResponse response = restClient.get()
                .uri("/v1/articles?boardId=1&pageSize=30&page=50000")
                .retrieve()
                .body(ArticlePageResponse.class);
        System.out.println("response.geta = " + response.getArticleCount());

        for (ArticleResponse article: response.getArticles()) {
            System.out.println("article = " + article);
        }
    }

    @Test
    void readAllInfiniteScrollTest(){
        List<ArticleResponse> response = restClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=30")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleResponse>>() {
                });

        for (ArticleResponse article: response) {
            System.out.println("article = " + article);
        }

        Long lastArticleId = response.getLast().getArticleId();

        List<ArticleResponse> response2 = restClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=30&lastArticleId=%s".formatted(lastArticleId))
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleResponse>>() {
                });

        for (ArticleResponse article: response2) {
            System.out.println("article2 = " + article);
        }
    }

    ArticleResponse create(ArticleCreateRequest request){
        return restClient.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    ArticleResponse read(Long articleId){
        return restClient.get()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponse.class);
    }

    ArticleResponse update(Long articleId){
        return restClient.put()
                .uri("/v1/articles/{articleId}", articleId)
                .body(new ArticleUpdateRequest("hi 222", "my content 22"))
                .retrieve()
                .body(ArticleResponse.class);
    }

    void delete(Long articleId){
        restClient.delete()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve();
    }

    @Test
    void countTest(){
        ArticleResponse response = create(new ArticleCreateRequest("hi", "content", 1L, 2L));

        Long count1 = restClient.get()
                .uri("/v1/articles/boards/{boardId}/count", response.getBoardId())
                .retrieve()
                .body(Long.class);
        System.out.println("count1 = " + count1);

        restClient.delete()
                .uri("/v1/articles/{articleId}", response.getArticleId())
                .retrieve();

        Long count2 = restClient.get()
                .uri("/v1/articles/boards/{boardId}/count", response.getBoardId())
                .retrieve()
                .body(Long.class);
        System.out.println("count1 = " + count2);
    }

    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequest {
        private String title;
        private String content;
        private Long writerId;
        private Long boardId;
    }

    @Getter
    @AllArgsConstructor
    static class ArticleUpdateRequest {
        private String title;
        private String content;
    }
}
