package uz.pdp.hotel.entity.request;

import jakarta.persistence.Entity;
import lombok.*;
import uz.pdp.hotel.entity.BaseEntity;

import java.util.Date;
import java.util.UUID;

@Entity(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class BookingRequest extends BaseEntity {
    private UUID userId;
    private UUID roomId;
    private Double price;
    private Date beginDate;
    private Date endDate;
}
