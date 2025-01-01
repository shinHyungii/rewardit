package kr.rewordit.api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.rewordit.api.model.Member;
import kr.rewordit.api.model.Shop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private final SecretKey secretKey;

    private final long ACCESS_TOKEN_EXPIRATION = 3 * 60 * 60L; // 3 시간

    private final long REFRESH_TOKEN_EXPIRATION = 24 * 60 * 60L; // 24 시간

    public JwtProvider(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    public String createAccessToken(Member member) {
        return this.createToken(null, member, ACCESS_TOKEN_EXPIRATION);
    }


    public String createAccessToken(Shop shop) {
        return this.createToken(shop, null, ACCESS_TOKEN_EXPIRATION);
    }


    public String createRefreshToken(Member member) {
        return this.createToken(null, member, REFRESH_TOKEN_EXPIRATION);
    }


    public String createRefreshToken(Shop shop) {
        return this.createToken(shop, null, REFRESH_TOKEN_EXPIRATION);
    }


    private String createToken(Shop shop, Member member, long expireSeconds) {
        ClaimsBuilder claimsBuilder = Jwts.claims();
        if (shop != null) {
            claimsBuilder.add("id", shop.getId());
            claimsBuilder.add("role", "ROLE_SHOP");
        }
        if (member != null) {
            claimsBuilder.add("id", member.getId());
            claimsBuilder.add("role", "ROLE_USER");
        }

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        ZonedDateTime tokenValidity = now.plusSeconds(expireSeconds);

        return Jwts.builder()
            .claims(claimsBuilder.build())
            .issuedAt(Date.from(now.toInstant()))
            .expiration(Date.from(tokenValidity.toInstant()))
            .signWith(secretKey)
            .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parse(token);

            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    public Long getUserId(String token) {
        return ((Claims) parseClaims(token)).get("id", Long.class);
    }

    public String getRole(String token) {
        return ((Claims) parseClaims(token)).get("role", String.class);
    }

    public Object parseClaims(String accessToken) {
        try {
            return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parse(accessToken)
                .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
