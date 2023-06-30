package uz.pdp.hotel.entity.room;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
    private Double pricePerNight;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "type")
    private RoomType type;
    private Boolean hasMonitor;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "status")
    private RoomStatus status;
}
