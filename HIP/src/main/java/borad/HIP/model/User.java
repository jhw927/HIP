package borad.HIP.model;

import borad.HIP.domain.UserEntity;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
//UserDto
public class User {
    private Long id;
    private String userName;
    private String password;
    private UserRole role;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    // Entity > DTO 로 변환하는 메소드
    public static User fromEntity(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getUserName(),
                userEntity.getPassword(),
                userEntity.getRole(),
                userEntity.getRegisteredAt(),
                userEntity.getUpdatedAt(),
                userEntity.getDeletedAt()
        );
    }

}
