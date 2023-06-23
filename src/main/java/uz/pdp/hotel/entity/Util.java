package uz.pdp.hotel.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Util {
    private List<String> authorities;
}
