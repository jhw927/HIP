package borad.HIP.service;

import borad.HIP.domain.UserEntity;
import borad.HIP.exception.ErrorCode;
import borad.HIP.exception.SnsException;
import borad.HIP.model.User;
import borad.HIP.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;

    private final BCryptPasswordEncoder encoder;
    @Transactional
    public User join(String userName,String password){
        // 회원가입하려는 userName으로 user가 있는지 조회
        userEntityRepository.findByUserName(userName).ifPresent(it ->{
            throw new SnsException(ErrorCode.DUPLICATED_USER_NAME,String.format("%s이 중복되었습니다.",userName));
        });

        // 회원가입 진행
        // userEntityRepository에 저장
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, encoder.encode(password)));


        return User.fromEntity(userEntity);
    }
    public String login(String userName, String password){
        // 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(()->new SnsException(ErrorCode.DUPLICATED_USER_NAME,""));
        // 비밀번호 체크
        if(!userEntity.getPassword().equals(password)){
            throw new SnsException(ErrorCode.DUPLICATED_USER_NAME,"");
        }

        // 토큰 생성
        return "--";

    }
}
