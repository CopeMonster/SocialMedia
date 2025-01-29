package me.alanton.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import me.alanton.authservice.dto.response.RoleResponse;
import me.alanton.authservice.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${auth.token.secret}")
    private String jwtSecret;

    @Value("${auth.token.access.expirationTime}")
    private Long accessTokenExpirationTime;

    @Value("${auth.token.refresh.expirationTime}")
    private Long refreshTokenExpirationTime;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        if (userDetails instanceof UserResponse userResponse) {
            claims.put("id", userResponse.id());
            claims.put("email", userResponse.email());
            claims.put("roles", userResponse.roles().stream()
                    .map(RoleResponse::name)
                    .collect(Collectors.toSet()));
        }

        return generateAccessToken(claims, userDetails);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        if (userDetails instanceof UserResponse userResponse) {
            claims.put("id", userResponse.id());
            claims.put("email", userResponse.email());
        }

        return generateRefreshToken(claims, userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolve) {
        final Claims claims = extractAllClaims(token);

        return claimsResolve.apply(claims);
    }

    public String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        final Instant expirationToken = LocalDateTime.now()
                .plusSeconds(accessTokenExpirationTime)
                .atZone(ZoneId.systemDefault())
                .toInstant();

        final Date expiration = Date.from(expirationToken);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(extraClaims)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        final Instant expirationToken = LocalDateTime.now()
                .plusSeconds(refreshTokenExpirationTime)
                .atZone(ZoneId.systemDefault())
                .toInstant();

        final Date expiration = Date.from(expirationToken);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(extraClaims)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}