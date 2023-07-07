package uz.pdp.hotel.repository.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.hotel.entity.card.CardType;

@Repository
public interface CardTypeRepository extends JpaRepository<CardType,String> {
}
