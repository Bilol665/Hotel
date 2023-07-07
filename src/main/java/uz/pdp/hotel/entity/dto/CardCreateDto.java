package uz.pdp.hotel.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CardCreateDto {
    @NotBlank(message = "Number cannot be blank!")
    @Pattern(regexp = "\\b(?:\\d[ -]*?){13,16}\\b")
    private String number;
    @NotBlank(message = "Expiry date cannot be blank!")
    private String expiryDate;
    @NotBlank(message = "Type cannot be blank!")
    private String type;
}
