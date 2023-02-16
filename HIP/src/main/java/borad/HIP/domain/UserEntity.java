package borad.HIP.domain;

import borad.HIP.model.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "\"user\"") // db에는 이미 user라는 테이블이 있기에 "\""\"를 사용한다.
@Getter
@Setter
@SQLDelete(sql = "UPDATE \"user\"  SET deleted_at = NOW() where id = ? ")
@Where(clause = "deleted_at is null ")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER; // 유저의 권한 정보

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

    // DB에 저장을 할때에만 Entity를 사용하며 서비스단에서는 DTO를 사용 이부분은 그 변화를 위해서 사용하는 메소드
    public static UserEntity of(String userName,String password){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setPassword(password);
        return userEntity;
    }


}
