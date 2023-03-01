package borad.HIP.controller.response;

import borad.HIP.model.Post;
import borad.HIP.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class PostResponse {
    private Long id;

    private UserResponse user;
    private String title;
    private String body;

    private Timestamp registeredAt;

    private Timestamp updatedAt;

    // softDelete 형식
    private Timestamp deletedAt;

    public static PostResponse fromPost(Post post) {
        return new PostResponse(
                post.getId(),
                UserResponse.fromUser(post.getUser()),
                post.getTitle(),
                post.getBody(),
                post.getRegisteredAt(),
                post.getUpdatedAt(),
                post.getDeletedAt()
        );
    }
}
