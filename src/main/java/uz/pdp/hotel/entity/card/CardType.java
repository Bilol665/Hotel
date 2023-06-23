package uz.pdp.hotel.entity.card;

import jakarta.persistence.Entity;
import lombok.*;
import uz.pdp.hotel.entity.BaseEntity;

@Entity(name = "card_types")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CardType extends BaseEntity {
    private String type;
}
