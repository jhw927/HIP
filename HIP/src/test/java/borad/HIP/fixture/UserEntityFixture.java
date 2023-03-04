package borad.HIP.fixture;

import borad.HIP.domain.UserEntity;

public class UserEntityFixture {
    public static UserEntity get(String userName, String password,Long userId){
        UserEntity result = new UserEntity();
        result.setId(userId);
        result.setUserName(userName);
        result.setPassword(password);

        return result;
    }
}
