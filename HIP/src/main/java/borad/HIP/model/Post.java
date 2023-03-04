package borad.HIP.model;

import borad.HIP.domain.PostEntity;
import borad.HIP.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.sql.Timestamp;
@Getter
@AllArgsConstructor
public class Post {

    private Long id;

    private User user;
    private String title;
    private String body;

    private Timestamp registeredAt;

    private Timestamp updatedAt;

    // softDelete 형식
    private Timestamp deletedAt;

    // Entity > DTO 로 변환하는 메소드
    public static Post fromEntity(PostEntity postEntity) {
        return new Post(
                postEntity.getId(),
                User.fromEntity(postEntity.getUser()),
                postEntity.getTitle(),
                postEntity.getBody(),
                postEntity.getRegisteredAt(),
                postEntity.getUpdatedAt(),
                postEntity.getDeletedAt()
        );
    }

}
