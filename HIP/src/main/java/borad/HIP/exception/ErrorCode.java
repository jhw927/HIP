package borad.HIP.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT,"유저이름이 중복 되었습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"User not found"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"Invalid password "),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"Internal server error")
    ;

    private HttpStatus status;
    private String message;
}
