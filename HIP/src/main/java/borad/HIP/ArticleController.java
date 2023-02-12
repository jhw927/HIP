package borad.HIP;

import borad.HIP.dto.req.ArticleRequestDto;
import borad.HIP.dto.res.ArticleResponseDto;
import borad.HIP.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;


    @PatchMapping("upload")
    public ResponseDto<ArticleResponseDto> uploadArticle(@RequestBody ArticleRequestDto req){
        articleService.uploadArticle(req);

        return null;

    }

}
