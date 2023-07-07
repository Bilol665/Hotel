package uz.pdp.hotel.service.card;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.hotel.repository.card.CardRepository;
import uz.pdp.hotel.repository.card.CardTypeRepository;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final CardTypeRepository cardTypeRepository;


}
