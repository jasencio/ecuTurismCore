package ec.tourismvisitplanner.core.security.services;

import ec.tourismvisitplanner.core.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Component
@RequiredArgsConstructor
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    private final String ROLES = "roles";

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract roles from JWT claims
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get(ROLES, List.class);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String getToken(UserDetails user) {
        return generateToken(new HashMap<>(), user, jwtExpiration);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, long validity) {
        if (userDetails instanceof User appUser) {
            List<String> roles = appUser.getRoles().stream()
                    .map(Enum::name)
                    .collect(Collectors.toList());
            extraClaims.put(ROLES, roles);
        }
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * validity))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public long getExpirationTime() {
        return 1000 * 60 * 60 * jwtExpiration;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserEmail(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}