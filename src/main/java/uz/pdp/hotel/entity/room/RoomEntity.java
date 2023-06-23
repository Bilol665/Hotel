package uz.pdp.hotel.entity.room;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.pdp.hotel.entity.BaseEntity;

@Entity(name = "rooms")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RoomEntity extends BaseEntity {
    private String number;
    private Integer size;
    private Double price;
    @ManyToOne
    private RoomType type;
    private Boolean hasMonitor;
    @ManyToOne
    private RoomStatus status;
}
