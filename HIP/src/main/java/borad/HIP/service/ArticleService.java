package borad.HIP.service;

import borad.HIP.ResponseDto;
import borad.HIP.domain.Article;
import borad.HIP.dto.req.ArticleRequestDto;
import borad.HIP.dto.res.ArticleResponseDto;
import borad.HIP.repository.ArticleCommentRepository;
import borad.HIP.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    @Autowired
    public ArticleService(ArticleRepository articleRepository, ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    public ResponseDto<ArticleResponseDto> uploadArticle(ArticleRequestDto req){
        Article article = new Article();

        articleRepository.save(article);

        return null;
    }

}
