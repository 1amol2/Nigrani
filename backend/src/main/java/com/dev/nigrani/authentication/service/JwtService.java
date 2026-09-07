package com.dev.nigrani.authentication.service;

import com.dev.nigrani.authentication.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 86_400_000L);
        return Jwts.builder().subject(user.getOfficialId()).claim("role", user.getRole().name())
                .issuedAt(now).expiration(expiry).signWith(signingKey()).compact();
    }

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
