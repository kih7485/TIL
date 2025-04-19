package kuke.board.comment.api;

import kuke.board.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;

public class CommentApiV2Test {
    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void create(){
        CommentResponse res1 = create(new CommentCreateRequestV2(1L, "my comment1", null, 1L));
        CommentResponse res2 = create(new CommentCreateRequestV2(1L, "my comment2", res1.getPath(), 1L));
        CommentResponse res3 = create(new CommentCreateRequestV2(1L, "my comment3", res2.getPath(), 1L));

        System.out.println("res1.getPath()() = " + res1.getPath());
        System.out.println("\tres2.getPath()() = " + res2.getPath());
        System.out.println("\t\tres3.getPath()() = " + res3.getPath());
    }

    CommentResponse create(CommentCreateRequestV2 request){
        return restClient.post()
                .uri("/v2/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Test
    void countTest(){
        CommentResponse commentResponse = create(new CommentCreateRequestV2(2L, "mycomment1", null, 1L));
        Long count1 = restClient.get()
                .uri("/v2/comments/articles/{articleId}/count", commentResponse.getArticleId())
                .retrieve()
                .body(Long.class);
        System.out.println("count1 = " + count1);
        restClient.delete()
                .uri("/v2/comments/{commentId}", commentResponse.getCommentId())
                .retrieve();

        Long count2 = restClient.get()
                .uri("/v2/comments/articles/{articleId}/count", commentResponse.getArticleId())
                .retrieve()
                .body(Long.class);

        System.out.println("count2 = " + count2);
    }

    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequestV2 {
        private Long articleId;
        private String content;
        private String parentPath;
        private Long writerId;
    }
}
