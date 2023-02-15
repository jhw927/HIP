package borad.HIP.controller.request;

import borad.HIP.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void join(@RequestBody UserJoinRequest req){
        userService.join(req.getUserName(), req.getPassword());
    }
}
