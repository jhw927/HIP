package borad.HIP.model;

import borad.HIP.domain.CommentEntity;
import borad.HIP.domain.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Long id;
    private String userName;
    private String comment;
    private Long postId;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    // softDelete 형식
    private Timestamp deletedAt;

    // Entity > DTO 로 변환하는 메소드
    public static Comment fromEntity(CommentEntity entity) {
        return new Comment(
                entity.getId(),
                entity.getComment(),
                entity.getUser().getUserName(),
                entity.getPost().getId(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }

}
