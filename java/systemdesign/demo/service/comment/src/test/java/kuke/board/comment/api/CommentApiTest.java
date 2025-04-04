package kuke.board.comment.api;


import kuke.board.comment.service.response.CommentPageResponse;
import kuke.board.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class CommentApiTest {

    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void create(){
        CommentResponse comment = createComment(new CommentCreateRequest(1L, "my comment1", null, 1L));
        CommentResponse comment2 = createComment(new CommentCreateRequest(1L, "my comment2", comment.getCommentId(), 1L));
        CommentResponse comment3 = createComment(new CommentCreateRequest(1L, "my comment3", comment.getCommentId(), 1L));

        System.out.println("comment = " + comment);
        System.out.println("comment2 = " + comment2);
        System.out.println("comment3 = " + comment3);

//        comment = CommentResponse(commentId=166177906098589696, content=my comment1, parentCommentId=166177906098589696, articleId=1, deleted=false, createdAt=2025-04-03T22:31:40.250086)
    //       comment2 = CommentResponse(commentId=166177906530603008, content=my comment2, parentCommentId=166177906098589696, articleId=1, deleted=false, createdAt=2025-04-03T22:31:40.353369)
    //        comment3 = CommentResponse(commentId=166177906568351744, content=my comment3, parentCommentId=166177906098589696, articleId=1, deleted=false, createdAt=2025-04-03T22:31:40.362394)

    }

    @Test
    void read(){
        CommentResponse comment = restClient.get()
                .uri("/v1/comments/{commentId}", 166177906098589696L)
                .retrieve()
                .body(CommentResponse.class);
        System.out.println("comment = " + comment);
    }

    CommentResponse createComment(CommentCreateRequest request){
        return restClient.post()
                .uri("/v1/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Test
    void readAll(){
        CommentPageResponse response = restClient.get()
                .uri("/v1/comments?articleId=1&page=1&pageSize=10")
                .retrieve()
                .body(CommentPageResponse.class);

        System.out.println("response = " + response);
        for (CommentResponse comment : response.getComments()) {
            if(!comment.getParentCommentId().equals(comment.getCommentId())){
                System.out.println("\t");
            }
            System.out.println("comment = " + comment);
        }

    }

    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequest {
        private Long articleId;
        private String content;
        private Long parentCommentId;
        private Long writerId;
    }
}
