package borad.HIP.controller;

import borad.HIP.controller.request.LoginRequest;
import borad.HIP.controller.request.UserJoinRequest;
import borad.HIP.controller.response.AlarmResponse;
import borad.HIP.controller.response.Response;
import borad.HIP.controller.response.UserJoinResponse;
import borad.HIP.controller.response.UserLoginResponse;
import borad.HIP.exception.ErrorCode;
import borad.HIP.exception.SnsException;
import borad.HIP.model.User;
import borad.HIP.service.AlarmService;
import borad.HIP.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import util.ClassUtils;
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AlarmService alarmService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest req) {
        User user = userService.join(req.getName(), req.getPassword());
        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody LoginRequest req) {
        String token = userService.login(req.getName(), req.getPassword());
        return Response.success(new UserLoginResponse(token));
    }

    @GetMapping("/alarm")
    public Response<Page<AlarmResponse>> alarm(Pageable pageable, Authentication auth) {
        User user = ClassUtils.getSafeCastInstance(auth.getPrincipal(), User.class).orElseThrow(() ->
                new SnsException(ErrorCode.INTERNAL_SERVER_ERROR, "Casting to USer class failed"));
        return Response.success(userService.alarmList(user.getId(), pageable).map(AlarmResponse::fromAlarm));
    }

    @GetMapping(value = "/alarm/subscribe")
    public SseEmitter subscribe(Authentication auth) {
        log.info("subscribe");
        User user = ClassUtils.getSafeCastInstance(auth.getPrincipal(), User.class).orElseThrow(() ->
                new SnsException(ErrorCode.INTERNAL_SERVER_ERROR, "Casting to USer class failed"));
        return alarmService.connectAlarm(user.getId());
    }
}
