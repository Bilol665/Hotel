package uz.pdp.hotel.service.card;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import uz.pdp.hotel.entity.card.CardEntity;
import uz.pdp.hotel.entity.card.CardType;
import uz.pdp.hotel.entity.dto.CardCreateDto;
import uz.pdp.hotel.entity.dto.CardUpdateDto;
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
import java.util.Objects;
import java.util.UUID;

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

    public CardEntity get(UUID cardId, Principal principal) {
        if(cardId == null)
            throw new DataNotFoundException("Id is not given!");
        CardEntity cardEntity = cardRepository.findById(cardId).orElseThrow(
                () -> new DataNotFoundException("Card not found!")
        );
        if(Objects.equals(cardEntity.getOwner().getEmail(),principal.getName()))
            return cardEntity;
        throw new DataNotFoundException("Card not found!");
    }

    public CardEntity update(CardUpdateDto cardCreateDto, UUID cardId, Principal principal, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        CardEntity cardEntity = cardRepository.findById(cardId).orElseThrow(
                () -> new DataNotFoundException("Card not found!")
        );
        if(!Objects.equals(cardEntity.getOwner().getEmail(),principal.getName()))
            throw new NotAcceptable("Not your card sorry!");
        modelMapper.map(cardCreateDto,cardEntity);
        return cardRepository.save(cardEntity);
    }

    public void delete(UUID cardId, Principal principal) {
        CardEntity cardEntity = cardRepository.findById(cardId).orElseThrow(
                () -> new DataNotFoundException("Card not found!")
        );
        if(!Objects.equals(cardEntity.getOwner().getEmail(),principal.getName()))
            throw new NotAcceptable("You cannot delete this card!");
        cardRepository.delete(cardEntity);
    }
}
