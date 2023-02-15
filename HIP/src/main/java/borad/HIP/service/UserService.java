package borad.HIP.service;

import borad.HIP.domain.UserEntity;
import borad.HIP.exception.SnsException;
import borad.HIP.model.User;
import borad.HIP.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;

    public User join(String userName,String password){
        // 회원가입하려는 userName으로 user가 있는지 조회
        userEntityRepository.findByUserName(userName).ifPresent(it ->{
            throw new SnsException();
        });

        // 회원가입 진행
        // userEntityRepository에 저장
        userEntityRepository.save(new UserEntity());


        return new User();
    }
    public String login(String userName, String password){
        // 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(()->new SnsException());
        // 비밀번호 체크
        if(!userEntity.getPasswrod().equals(password)){
            throw new SnsException();
        }

        // 토큰 생성
        return "--";

    }
}
