package borad.HIP.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
public class ArticleComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30)
    private String title;
    @ManyToOne
    @JoinColumn(name = "Article_ID")
    private Article article;
    @Column(nullable = false)
    private String content;
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

    public ArticleComment() {
    }

    public ArticleComment(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    @Override // 동일 객체인지 확인하기 위해 사(참조값이 다르더라도 내부 값이 같으면 true 반환)
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleComment that = (ArticleComment) o;
        return id.equals(that.id);
    }

    @Override // 해시코드값 비교를 위해서 사용
    public int hashCode() {
        return Objects.hash(id);
    }
}
