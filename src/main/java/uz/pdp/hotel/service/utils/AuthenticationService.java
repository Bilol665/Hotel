package uz.pdp.hotel.service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    public void Authenticate(Claims claims, HttpServletRequest request) throws JsonProcessingException {
        List<String> roles = (List<String>) claims.get("roles");
        String username = claims.getSubject();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        getRoles(roles)
                );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    private List<SimpleGrantedAuthority> getRoles(List<String> roles) throws JsonProcessingException {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
