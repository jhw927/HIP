package borad.HIP.repository;

import borad.HIP.domain.PostEntity;
import borad.HIP.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity,Long> {
    Optional<PostEntity> findById(Long id);

    Page<PostEntity> findAllByUser(UserEntity entity, Pageable pageable);
}
