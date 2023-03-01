package borad.HIP.controller;

import borad.HIP.controller.request.PostModifyRequest;
import borad.HIP.controller.request.PostRequest;
import borad.HIP.controller.response.PostResponse;
import borad.HIP.controller.response.Response;
import borad.HIP.model.Post;
import borad.HIP.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping
    public Response<Void> create(@RequestBody PostRequest request, Authentication authentication){
        postService.create(request.getTitle(), request.getBody(), authentication.getName());
        return Response.success();
    }

    @PutMapping("/{postId}")
    public Response<PostResponse> modify(@PathVariable Long postId, @RequestBody PostModifyRequest request, Authentication authentication){
        Post post = postService.modify(request.getTitle(), request.getBody(), authentication.getName(),postId);
        return Response.success(PostResponse.fromPost(post));
    }
}
