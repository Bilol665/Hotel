package uz.pdp.hotel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel.service.user.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    @PutMapping("/block/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> block(
            @PathVariable UUID userId
    ) {
        userService.block(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/block/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> unBlock(
            @PathVariable UUID userId
    ) {
        userService.unBlock(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
