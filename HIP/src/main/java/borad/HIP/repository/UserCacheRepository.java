package borad.HIP.repository;

import borad.HIP.model.User;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserCacheRepository {

    private final RedisTemplate<String, User> userRedisTemplate;
    private final Duration USER_CACHE_TTL = Duration.ofDays(3);

    public void setUser(User user){
        String key = getKey(user.getUsername());
        log.info("Set User to Redis {}:{}", key, user);
        userRedisTemplate.opsForValue().set(key,user);

    }

    public Optional<User> getUser(String userName){
        String key = getKey(userName);
        User user = userRedisTemplate.opsForValue().get(key);
        log.info("Get data from Redis {}:{}", key, user);
        return Optional.ofNullable(user);
    }

    private String getKey(String userName){
        return "USER:" + userName;  // 구분을 위한 prefix를 넣어줄것
    }
}
