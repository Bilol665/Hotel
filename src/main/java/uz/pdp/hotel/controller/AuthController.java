package uz.pdp.hotel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel.entity.dto.LoginDto;
import uz.pdp.hotel.entity.dto.UserCreateDto;
import uz.pdp.hotel.entity.response.JwtResponse;
import uz.pdp.hotel.entity.user.UserEntity;
import uz.pdp.hotel.service.user.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserEntity> signUp(
            @RequestBody UserCreateDto userCreateDto
    ) {
        return ResponseEntity.ok(userService.save(userCreateDto));
    }
    @GetMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody LoginDto loginDto
    ) {
        return ResponseEntity.ok(userService.login(loginDto));
    }

}
