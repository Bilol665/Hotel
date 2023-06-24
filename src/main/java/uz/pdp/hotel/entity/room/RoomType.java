package uz.pdp.hotel.entity.room;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "room_types")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RoomType {
    @Id
    @Column(unique = true)
    private String type;
}
