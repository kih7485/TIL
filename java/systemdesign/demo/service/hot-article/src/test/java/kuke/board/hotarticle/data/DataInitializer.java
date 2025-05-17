package kuke.board.hotarticle.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.random.RandomGenerator;

public class DataInitializer {
    RestClient articleServiceClient = RestClient.create("http://localhost:9000");
    RestClient commentServiceClient = RestClient.create("http://localhost:9001");
    RestClient likeServiceClient = RestClient.create("http://localhost:9002");
    RestClient viewServiceClient = RestClient.create("http://localhost:9003");

    @Test
    void initialize(){
        for (int i=0; i<30; i++){
            Long articleId = createArticle();
            int commentCount = RandomGenerator.getDefault().nextInt(10);
            int likeCount = RandomGenerator.getDefault().nextInt(10);
            int viewCount = RandomGenerator.getDefault().nextInt(200);

            createComment(articleId, commentCount);
            like(articleId, likeCount);
            view(articleId, viewCount);
        }
    }

    void view(Long articleId, int viewCount) {
        while (viewCount-- > 0){
            viewServiceClient.post()
                    .uri("/v1/article-views/articles/{articleId}/users/{userId}", articleId, viewCount)
                    .retrieve();
        }
    }

    void like(Long articleId, int likeCount) {
        while (likeCount-- > 0){
            likeServiceClient.post()
                    .uri("/v1/article-likes/articles/{articleId}/users/{userId}/pessimistic-lock-1", articleId, likeCount)
                    .retrieve();
        }
    }

    void createComment(Long articleId, int commentCount) {
        while (commentCount-- > 0){
            commentServiceClient.post()
                    .uri("/v2/comments")
                    .body(new CommentCreateRequest(articleId,"content",  1L))
                    .retrieve();
        }
    }

    Long createArticle(){
        return articleServiceClient.post()
                .uri("/v1/articles")
                .body(new ArticleCreateRequest("title", "content", 1L, 1L))
                .retrieve()
                .body(ArticleResponse.class)
                .getArticleId();
    }

    @Getter
    @AllArgsConstructor
    static class CommentCreateRequest{
        private Long articleId;
        private String content;
        private Long writerId;
    }

    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequest{
        private String title;
        private String content;
        private Long writerId;
        private Long boardId;
    }
    @Getter
    static class ArticleResponse{
        private Long articleId;
    }
}
