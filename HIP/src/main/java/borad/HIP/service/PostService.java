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
import org.springframework.web.bind.annotation.PostMapping;

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
}
