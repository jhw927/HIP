package borad.HIP.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT,"유저이름이 중복 되었습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"User not found"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"Post not found"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"Invalid password"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED,"Permission is Invalid"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"Internal server error"),
    ALREADY_LIKE_CHECKED(HttpStatus.CONFLICT,"User already like checked")
    ;

    private HttpStatus status;
    private String message;
}
