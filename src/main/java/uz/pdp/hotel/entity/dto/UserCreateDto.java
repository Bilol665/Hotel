package uz.pdp.hotel.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserCreateDto {
    @NotBlank(message = "Email cannot be blank")
    private String email;
    private String name;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    private Integer age;
}
