package uz.pdp.hotel.entity.room;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "room_status")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RoomStatus {
    @Id
    @Column(unique = true)
    private String status;
}
