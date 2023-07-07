package uz.pdp.hotel.entity.card;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import uz.pdp.hotel.entity.BaseEntity;

@Entity(name = "card_types")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CardType {
    @Id
    @Column(unique = true)
    private String type;
}
