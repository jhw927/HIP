package borad.HIP.controller;

import borad.HIP.controller.request.UserJoinRequest;
import borad.HIP.controller.response.Response;
import borad.HIP.controller.response.UserJoinResponse;
import borad.HIP.model.User;
import borad.HIP.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response <UserJoinResponse> join(@RequestBody UserJoinRequest req){
        User user = userService.join(req.getUserName(), req.getPassword());
        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping("/loin")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest req){
        return null;
    }
}
