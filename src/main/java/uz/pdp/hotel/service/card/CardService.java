package uz.pdp.hotel.service.card;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import uz.pdp.hotel.entity.card.CardEntity;
import uz.pdp.hotel.entity.card.CardType;
import uz.pdp.hotel.entity.dto.CardCreateDto;
import uz.pdp.hotel.exceptions.DataNotFoundException;
import uz.pdp.hotel.exceptions.NotAcceptable;
import uz.pdp.hotel.exceptions.RequestValidationException;
import uz.pdp.hotel.repository.card.CardRepository;
import uz.pdp.hotel.repository.card.CardTypeRepository;
import uz.pdp.hotel.repository.user.UserRepository;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final CardTypeRepository cardTypeRepository;
    private final ModelMapper modelMapper;

    public CardEntity addCard(CardCreateDto cardCreateDto, Principal principal, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        CardEntity cardEntity = modelMapper.map(cardCreateDto, CardEntity.class);
        cardEntity.setBalance(0D);
        cardEntity.setOwner(userRepository.findUserEntityByEmail(principal.getName()).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        ));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cardEntity.setExpiryDate(dateFormat.parse(cardCreateDto.getExpiryDate()));
        } catch (ParseException e) {
            throw new NotAcceptable("Invalid date format!");
        }
        cardEntity.setType(cardTypeRepository.findById(cardCreateDto.getType()).orElseGet(
                () -> cardTypeRepository.save(new CardType(cardCreateDto.getType()))
        ));
        return cardRepository.save(cardEntity);
    }
}
