package uz.pdp.hotel.entity.card;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.hotel.entity.BaseEntity;
import uz.pdp.hotel.entity.user.UserEntity;

import java.time.LocalDateTime;

@Entity(name = "cards")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CardEntity extends BaseEntity {
    private String number;
    private Double balance;
    private LocalDateTime expiryDate;
    @ManyToOne
    private UserEntity owner;
    @OneToOne(cascade = CascadeType.ALL)
    private CardType type;
}
