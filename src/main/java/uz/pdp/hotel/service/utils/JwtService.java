package uz.pdp.hotel.service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import uz.pdp.hotel.entity.user.UserEntity;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.secret.key}")
    private String key;
    @Value("${jwt.access.expiry}")
    private long accessTokenExpiry;

    public String  generateAccessToken(UserEntity userEntity) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,key)
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
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }
}
