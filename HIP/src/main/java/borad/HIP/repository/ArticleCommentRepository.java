package borad.HIP.repository;

import borad.HIP.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment,Long> {
}
