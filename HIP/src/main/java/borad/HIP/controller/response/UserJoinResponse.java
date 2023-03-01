package borad.HIP.controller.response;

import borad.HIP.model.User;
import borad.HIP.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UserJoinResponse {
    private Long id;
    private String  userName;
    private UserRole userRole;

    public static UserJoinResponse fromUser(User user){
        return new UserJoinResponse(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }
}
