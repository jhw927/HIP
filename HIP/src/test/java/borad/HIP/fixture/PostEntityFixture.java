package borad.HIP.fixture;

import borad.HIP.domain.PostEntity;
import borad.HIP.domain.UserEntity;

public class PostEntityFixture {
    public static PostEntity get(String userName, Long postId, Long userId){
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUserName(userName);


        PostEntity result = new PostEntity();
        result.setUser(user);
        result.setId(postId);

        return result;
    }
}
