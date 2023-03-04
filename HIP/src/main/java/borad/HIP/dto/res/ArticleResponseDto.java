package borad.HIP.dto.res;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class ArticleResponseDto {
    private String title;
    private String content;
    private String hashtag;
    private LocalDateTime createdAt;
}
