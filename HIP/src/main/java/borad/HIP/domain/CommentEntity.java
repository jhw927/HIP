package borad.HIP.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "\"comment\"", indexes = {
        @Index(name = "post_id_idx",columnList = "post_id")
})
@SQLDelete(sql = "UPDATE post SET deleted_at = NOW() WHERE id=?")
@Where(clause = "deleted_at is null")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;
    @Column(name = "comment")
    private String comment;

    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // softDelete 형식
    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registeredAt(){
        this.registeredAt=Timestamp.from(Instant.now());
    }
    @PreUpdate
    void updatedAt(){
        this.updatedAt=Timestamp.from(Instant.now());
    }

    public static CommentEntity of(PostEntity postEntity, UserEntity userEntity,String comment){
        CommentEntity entity = new CommentEntity();
        entity.setPost(postEntity);
        entity.setUser(userEntity);
        entity.setComment(comment);
        return entity;
    }

}
