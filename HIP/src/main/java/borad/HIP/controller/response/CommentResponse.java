package borad.HIP.controller.response;

import borad.HIP.model.Comment;
import borad.HIP.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String comment;
    private String userName;
    private Long postId;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    // softDelete 형식
    private Timestamp deletedAt;

    public static CommentResponse fromComment(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getUserName(),
                comment.getComment(),
                comment.getPostId(),
                comment.getRegisteredAt(),
                comment.getUpdatedAt(),
                comment.getDeletedAt()
        );
    }
}
