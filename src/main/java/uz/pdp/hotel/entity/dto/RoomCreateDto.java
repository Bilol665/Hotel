package uz.pdp.hotel.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RoomCreateDto {
    @NotBlank(message = "Number cannot be blank")
    private String number;
    private Integer size;
    @NotNull(message = "Price should be set")
    private Double price;
    private String type;
    private Boolean hasMonitor;
    private String status;
}
