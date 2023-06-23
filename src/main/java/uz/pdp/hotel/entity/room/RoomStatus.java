package uz.pdp.hotel.entity.room;

import jakarta.persistence.Entity;
import lombok.*;
import uz.pdp.hotel.entity.BaseEntity;

@Entity(name = "room_status")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RoomStatus extends BaseEntity {
    private String status;
}
