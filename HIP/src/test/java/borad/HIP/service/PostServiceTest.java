package borad.HIP.service;

import borad.HIP.domain.PostEntity;
import borad.HIP.domain.UserEntity;
import borad.HIP.exception.ErrorCode;
import borad.HIP.exception.SnsException;
import borad.HIP.fixture.PostEntityFixture;
import borad.HIP.fixture.UserEntityFixture;
import borad.HIP.repository.PostEntityRepository;
import borad.HIP.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {
    @Autowired
    private PostService postService;
    @MockBean
    UserEntityRepository userEntityRepository;
    @MockBean
    PostEntityRepository postEntityRepository;

    @Test
    void 포스트작성_성공한경우(){
        String title = "title";
        String body = "body";
        String userName = "userName";

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));
    }


    @Test
    void 포스트작성을_요청한유저가_존재하지않는경우(){
        String title = "title";
        String body = "body";
        String userName = "userName";

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        SnsException e = Assertions.assertThrows(SnsException.class, ()-> postService.create(title,body,userName));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND,e.getErrorCode());
    }

    @Test
    void 포스트수정이_성공한경우(){
        String title = "title";
        String body = "body";
        String userName = "userName";
        Long postId = 1L;

        PostEntity postEntity = PostEntityFixture.get(userName,postId,1L);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));
        when(postEntityRepository.save(any())).thenReturn(postEntity);

        Assertions.assertDoesNotThrow(()-> postService.modify(title,body,userName,postId));
    }

    @Test
    void 포스트수정시_포스트가_존재하지않는_경우(){
        String title = "title";
        String body = "body";
        String userName = "userName";
        Long postId = 1L;

        PostEntity postEntity = PostEntityFixture.get(userName,postId,1L);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.empty());

        SnsException e = Assertions.assertThrows(SnsException.class, ()-> postService.modify(title,body,userName,postId));
        Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 포스트수정시_권한이_없는_경우(){
        String title = "title";
        String body = "body";
        String userName = "userName";
        Long postId = 1L;

        PostEntity postEntity = PostEntityFixture.get(userName,postId,1L);
        UserEntity writer = UserEntityFixture.get("writer","password",2L);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(writer));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        SnsException e = Assertions.assertThrows(SnsException.class, ()-> postService.modify(title,body,userName,postId));
        Assertions.assertEquals(ErrorCode.INVALID_PASSWORD, e.getErrorCode());
    }
}
