package borad.HIP.repository;

import borad.HIP.domain.LikeEntity;
import borad.HIP.domain.PostEntity;
import borad.HIP.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity,Long> {
    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

    List<LikeEntity> findAllByPost(PostEntity post);
    @Query(value = "SELECT COUNT(*) FROM LikeEntity entity WHERE entity.post = :post")
    Integer countByPost(@Param("post") PostEntity post);

}
