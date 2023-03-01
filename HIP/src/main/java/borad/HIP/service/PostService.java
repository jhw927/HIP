package borad.HIP.service;

import borad.HIP.domain.PostEntity;
import borad.HIP.domain.UserEntity;
import borad.HIP.exception.ErrorCode;
import borad.HIP.exception.SnsException;
import borad.HIP.repository.PostEntityRepository;
import borad.HIP.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(String title, String body, String userName){
        // 유저확인
        UserEntity user = userEntityRepository.findByUserName(userName).orElseThrow(()->
                new SnsException(ErrorCode.USER_NOT_FOUND,String.format("%s not found",userName)));

        // 포스트 저장
        postRepository.save(PostEntity.of(title,body,user));
    }
    public void modify(String title, String body, String userName, Long id){
        // 유저확인
        UserEntity user = userEntityRepository.findByUserName(userName).orElseThrow(()->
                new SnsException(ErrorCode.USER_NOT_FOUND,String.format("%s not found",userName)));
        // 게시글 존재확인
        Optional<PostEntity> post = postRepository.findById(id);
        // 본인의 게시글이 맞는지 확인
        //수정
    }
}
