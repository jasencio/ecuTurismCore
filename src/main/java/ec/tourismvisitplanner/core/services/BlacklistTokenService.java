package ec.tourismvisitplanner.core.services;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
public class BlacklistTokenService {
    private final StringRedisTemplate redisTemplate;

    public BlacklistTokenService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void blacklistToken(String token, long expirationSeconds) {
        redisTemplate.opsForValue().set(token, "blacklisted", Duration.ofSeconds(expirationSeconds));
    }

    public boolean isTokenBlacklisted(String token) {
        return redisTemplate.hasKey(token);
    }
}