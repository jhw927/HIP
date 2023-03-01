package borad.HIP.repository;

import borad.HIP.domain.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity,Long> {
    Optional<PostEntity> findById(Long id);
}
