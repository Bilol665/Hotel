package uz.pdp.hotel.entity.card;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import uz.pdp.hotel.entity.BaseEntity;
import uz.pdp.hotel.entity.user.UserEntity;

import java.util.Date;

@Entity(name = "cards")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class CardEntity extends BaseEntity {
    private String number;
    private Double balance;
    private Date expiryDate;
    @ManyToOne
    private UserEntity owner;
    @OneToOne(cascade = CascadeType.PERSIST)
    private CardType type;
}
