package uz.pdp.hotel.entity.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.pdp.hotel.entity.BaseEntity;
import uz.pdp.hotel.entity.room.RoomEntity;
import uz.pdp.hotel.entity.user.UserEntity;

import java.util.Date;

@Entity(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class BookingRequest extends BaseEntity {
    @ManyToOne
    @JsonIgnore
    private UserEntity user;
    @ManyToOne(cascade = CascadeType.ALL)
    private RoomEntity room;
    @JsonIgnore
    private Double totalPrice;
    @JsonIgnore
    private Date beginDate;
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;
}
