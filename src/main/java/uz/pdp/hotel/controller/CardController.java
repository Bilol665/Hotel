package uz.pdp.hotel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.hotel.entity.card.CardEntity;
import uz.pdp.hotel.entity.dto.CardCreateDto;
import uz.pdp.hotel.service.card.CardService;

import java.security.Principal;

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
}
