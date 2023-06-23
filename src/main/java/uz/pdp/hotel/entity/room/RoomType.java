package uz.pdp.hotel.entity.room;

import jakarta.persistence.Entity;
import lombok.*;
import uz.pdp.hotel.entity.BaseEntity;

@Entity(name = "room_types")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RoomType extends BaseEntity {
    private String type;
}
