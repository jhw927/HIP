package borad.HIP.service;

import borad.HIP.domain.*;
import borad.HIP.model.Comment;
import borad.HIP.model.Post;
import borad.HIP.exception.ErrorCode;
import borad.HIP.exception.SnsException;
import borad.HIP.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postRepository;
    private final UserEntityRepository userEntityRepository;
    private final LikeEntityRepository likeRepo;
    private final CommentEntityRepository commentRepo;
    private final AlarmEntityRepository alarmRepo;

    @Transactional
    public void create(String title, String body, String userName) {
        // 유저확인
        UserEntity user = getUserOrException(userName);

        // 포스트 저장
        postRepository.save(PostEntity.of(title, body, user));
    }

    @Transactional
    public Post modify(String title, String body, String userName, Long id) {
        // 유저확인
        UserEntity user = getUserOrException(userName);
        // 게시글 존재확인
        PostEntity post = getPostOrException(id);
        // 본인의 게시글이 맞는지 확인
        if (post.getUser() != user) {
            throw new SnsException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, id));
        }
        //수정
        post.setTitle(title);
        post.setBody(body);

        return Post.fromEntity(postRepository.saveAndFlush(post));

    }

    @Transactional
    public void delete(String userName, Long postId) {
        UserEntity user = getUserOrException(userName);
        PostEntity post = getPostOrException(postId);

        if (post.getUser() != user) {
            throw new SnsException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
        }
        likeRepo.deleteAllByPost(post);
        commentRepo.deleteAllByPost(post);
        postRepository.delete(post);
    }

    public Page<Post> list(Pageable pageable) {
        return postRepository.findAll(pageable).map(Post::fromEntity);
    }

    public Page<Post> my(String userName, Pageable pageable) {
        UserEntity user = getUserOrException(userName);

        return postRepository.findAllByUser(user, pageable).map(Post::fromEntity);
    }

    @Transactional
    public void like(Long postId, String userName) {
        UserEntity user = getUserOrException(userName);
        PostEntity post = getPostOrException(postId);

        // 좋아요 체크
        likeRepo.findByUserAndPost(user, post).ifPresent(it -> {
            throw new SnsException(ErrorCode.ALREADY_LIKE_CHECKED, String.format("userName %s already like post %d", userName, postId));
        });

        likeRepo.save(LikeEntity.of(post, user));

        alarmRepo.save(AlarmEntity.of(post.getUser(),AlarmType.NEW_LIKE_ON_POST));
    }

    public long likeCnt(Long postId) {
        PostEntity post = getPostOrException(postId);
        // 좋아요 수 체크
//        List<LikeEntity> likes = likeRepo.findAllByPost(post);
//        return likes.size();
        return likeRepo.countByPost(post);
    }

    @Transactional
    public void comment(Long postId,String comment,String userName){
        UserEntity user = getUserOrException(userName);
        PostEntity post = getPostOrException(postId);

        // comment save
        commentRepo.save(CommentEntity.of(post,user,comment));
        // 알람 발생
        alarmRepo.save(AlarmEntity.of(post.getUser(),AlarmType.NEW_COMMENT_ON_POST));
    }

//    자주 사용되는 post 존재 확인 및 유저 확인 로직을 메소드로 따로 만듬 > 코드의 반복 저하
    private PostEntity getPostOrException(Long postId){
        return  postRepository.findById(postId).orElseThrow(() ->
                new SnsException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));
    }
    private UserEntity getUserOrException(String userName){
        return userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", userName)));
    }

    public Page<Comment> getComment(Long postId, Pageable pageable) {
        PostEntity post = getPostOrException(postId);
        return commentRepo.findAllByPost(post,pageable).map(Comment::fromEntity);

    }
}
