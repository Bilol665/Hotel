package uz.pdp.hotel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel.entity.card.CardEntity;
import uz.pdp.hotel.entity.dto.CardCreateDto;
import uz.pdp.hotel.entity.dto.CardUpdateDto;
import uz.pdp.hotel.service.card.CardService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/card")
public class CardController {
    private final CardService cardService;
    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CardEntity> addCard(
            @Valid @RequestBody CardCreateDto cardCreateDto,
            Principal principal,
            BindingResult bindingResult
    ) {
        return ResponseEntity.ok(cardService.addCard(cardCreateDto,principal,bindingResult));
    }
    @GetMapping("/get")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CardEntity> get(
            @RequestParam(required = false,defaultValue = "") UUID cardId,
            Principal principal
    ) {
        return ResponseEntity.ok(cardService.get(cardId,principal));
    }
    @PutMapping("/update/{cardId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CardEntity> update(
            @Valid @RequestBody CardUpdateDto cardCreateDto,
            @PathVariable UUID cardId,
            Principal principal,
            BindingResult bindingResult
    ){
        return ResponseEntity.ok(cardService.update(cardCreateDto,cardId,principal,bindingResult));
    }
    @DeleteMapping("/delete/{cardId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<HttpStatus> delete(
            @PathVariable UUID cardId,
            Principal principal
    ) {
        cardService.delete(cardId,principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
