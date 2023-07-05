package uz.pdp.hotel.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RequestDto {
    @NotNull(message = "room-id cannot be null")
    private UUID roomId;
    @NotBlank(message = "End date cannot be null")
    private String endDate;
}
