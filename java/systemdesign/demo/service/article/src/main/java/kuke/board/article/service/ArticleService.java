package kuke.board.article.service;

import kuke.board.article.entity.Article;
import kuke.board.article.repository.ArticleRespository;
import kuke.board.article.service.request.ArticleCreateRequest;
import kuke.board.article.service.request.ArticleUpdateRequest;
import kuke.board.article.service.response.ArticlePageResponse;
import kuke.board.article.service.response.ArticleResponse;
import kuke.board.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final Snowflake snowflake = new Snowflake();
    private final ArticleRespository articleRespository;

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request){
        Article article = articleRespository.save(
                 Article.create(
                         snowflake.nextId(),
                         request.getTitle(),
                         request.getContent(),
                         request.getBoardId(),
                         request.getWriterId()
                 )
         );
         return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse update(Long articleId, ArticleUpdateRequest request){
        Article article = articleRespository.findById(articleId).orElseThrow();
        article.update(request.getTitle(), request.getContent());
        return ArticleResponse.from(article);
    }

    public ArticleResponse read(Long articleId){
        return ArticleResponse.from(articleRespository.findById(articleId).orElseThrow());
    }

    @Transactional
    public void delete(Long articleId){
        articleRespository.deleteById(articleId);
    }

    public ArticlePageResponse readAll(Long boardId, Long page, Long pageSize){
        return ArticlePageResponse.of(
                articleRespository.findAll(boardId, (page-1) * pageSize, pageSize)
                        .stream()
                        .map(ArticleResponse::from)
                        .toList(),
                articleRespository.count(boardId, PageLimitCalculator.calculatePageLimit(page,pageSize, 10L))
                );
    }

    public List<ArticleResponse> readAllInfiniteScroll(Long boardId, Long pageSize, Long lastArticleId){
        List<Article> articles = lastArticleId == null ?
                articleRespository.findAllInfiniteScroll(boardId, pageSize) :
                articleRespository.findAllInfiniteScroll(boardId, pageSize, lastArticleId);

        return articles.stream().map(ArticleResponse::from).toList();
    }
}
