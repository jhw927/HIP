package borad.HIP.domain;

import borad.HIP.dto.req.ArticleRequestDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;
    @Column(nullable = false)
    private String content;
    @OneToMany(mappedBy = "article")
    private List<ArticleComment> articleComments = new ArrayList<>();
    @Column(nullable = false)
    private String hashtag;
    @Column
    private LocalDateTime createdAt; // 생성시간
    @Column
    private String createdBy;  // 작성자
    @Column
    private LocalDateTime modifiedAt; // 수정시간
    @Column
    private String modifiedBy; // 수정자


    public Article() {
    }

    public Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
