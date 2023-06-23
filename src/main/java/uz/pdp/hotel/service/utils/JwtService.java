package uz.pdp.hotel.service.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import uz.pdp.hotel.entity.user.UserEntity;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

@Service
public class JwtService {
    @Value("${jwt.secret.key}")
    private String key;
    @Value("${jwt.access.expiry}")
    private long accessTokenExpiry;

    public String  generateAccessToken(UserEntity userEntity) {
        return new DefaultJwtBuilder()
                .signWith(SignatureAlgorithm.ES512,Base64.getDecoder().decode(key))
                .setSubject(userEntity.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + accessTokenExpiry))
                .addClaims(Map.of("roles",getRoles(userEntity.getAuthorities())))
                .compact();
    }

    private List<String> getRoles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
    public Jws<Claims> extractToken(String token) {
        return Jwts.parserBuilder().setSigningKey(Base64.getDecoder().decode(key)).build().parseClaimsJws(token);
    }
}
