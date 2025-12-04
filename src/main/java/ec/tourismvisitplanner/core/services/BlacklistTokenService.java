package ec.tourismvisitplanner.core.services;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.time.Duration;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;

@Service
@Slf4j
public class BlacklistTokenService {
    private final StringRedisTemplate redisTemplate;

    public BlacklistTokenService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Retryable(retryFor = { Exception.class }, maxAttempts = 5, backoff = @Backoff(delay = 1000))
    public void blacklistToken(String token, long expirationSeconds) {
        redisTemplate.opsForValue().set(token, "blacklisted", Duration.ofSeconds(expirationSeconds));
    }

    @Recover
    public void recoverBlacklistToken(Exception e, String token, long expirationSeconds) {
        log.error("Failed to blacklist token after retries due to Redis error: {}", e.getMessage());
    }

    @Retryable(retryFor = { Exception.class }, maxAttempts = 5, backoff = @Backoff(delay = 1000))
    public boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }

    @Recover
    public boolean recoverIsTokenBlacklisted(Exception e, String token) {
        log.error("Failed to check if token is blacklisted after retries due to Redis error: {}", e.getMessage());
        return false;
    }
}