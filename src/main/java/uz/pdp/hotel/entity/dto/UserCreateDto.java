package uz.pdp.hotel.entity.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserCreateDto {
    private String email;
    private String name;
    private String password;
    private Integer age;
}
