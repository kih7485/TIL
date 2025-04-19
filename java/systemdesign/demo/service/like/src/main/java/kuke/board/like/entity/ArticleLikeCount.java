package kuke.board.like.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "article_like_count")
@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleLikeCount {
    @Id
    private Long articleId; //shard key
    private Long likeCount;
    @Version
    private Long version;

    public static ArticleLikeCount init(Long articleId, Long likeCount){
        ArticleLikeCount articleLikeCount = new ArticleLikeCount();
        articleLikeCount.articleId = articleId;
        articleLikeCount.likeCount = likeCount;
        articleLikeCount.version = 0L;
        return articleLikeCount;
    }

    public void increase(){
        likeCount++;
    }

    public void decrease(){
        likeCount--;
    }
}
