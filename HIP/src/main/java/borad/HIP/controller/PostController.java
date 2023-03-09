package borad.HIP.controller;

import borad.HIP.controller.request.PostModifyRequest;
import borad.HIP.controller.request.PostRequest;
import borad.HIP.controller.response.PostResponse;
import borad.HIP.controller.response.Response;
import borad.HIP.model.Post;
import borad.HIP.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping
    public Response<Void> create(@RequestBody PostRequest request, Authentication authentication) {
        postService.create(request.getTitle(), request.getBody(), authentication.getName());
        return Response.success();
    }

    @PutMapping("/{postId}")
    public Response<PostResponse> modify(@PathVariable Long postId, @RequestBody PostModifyRequest request, Authentication authentication) {
        Post post = postService.modify(request.getTitle(), request.getBody(), authentication.getName(), postId);
        return Response.success(PostResponse.fromPost(post));
    }

    @DeleteMapping("/{postId}")
    public Response<Void> delete(@PathVariable Long postId, Authentication authentication) {
        postService.delete(authentication.getName(), postId);
        return Response.success();
    }

    @GetMapping
    public Response<Page<PostResponse>> list(Pageable pageable, Authentication auth){
        return Response.success(postService.list(pageable).map(PostResponse::fromPost));
    }
    @GetMapping("/my")
    public Response<Page<PostResponse>> my(Pageable pageable, Authentication auth){
        return Response.success(postService.my(auth.getName(), pageable).map(PostResponse::fromPost));
    }
    @PostMapping("/{postId/likes")
    public Response<Void>like(@PathVariable Long postId,Authentication auth){
        postService.like(postId,auth.getName());
        return Response.success();
    }
    @GetMapping("/{postId/likes")
    public Response<Integer>likeCnt(@PathVariable Long postId,Authentication auth){
        return Response.success(postService.likeCnt(postId));
    }
}
