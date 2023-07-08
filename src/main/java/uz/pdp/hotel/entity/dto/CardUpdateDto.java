package uz.pdp.hotel.entity.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CardUpdateDto {
    @Pattern(regexp = "\\b(?:\\d[ -]*?){13,16}\\b")
    private String number;
    private String expiryDate;
    private String type;
}
