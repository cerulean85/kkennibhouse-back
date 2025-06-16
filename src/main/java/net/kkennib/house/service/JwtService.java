package net.kkennib.house.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;


@Service
public class JwtService {
    private final String secret = "your-secret-key";

    public String createAccessToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(Date.from(Instant.now().plusSeconds(60 * 15)))
                .sign(Algorithm.HMAC256(secret));
    }

    public String createRefreshToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(Date.from(Instant.now().plusSeconds(60 * 60 * 24 * 7)))
                .sign(Algorithm.HMAC256(secret));
    }

    public String verifyToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token).getSubject();
    }
}