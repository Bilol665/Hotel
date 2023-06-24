package uz.pdp.hotel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<UserEntity> signUpUser(
            @Valid @RequestBody UserCreateDto userCreateDto,
            BindingResult bindingResult
    ) {
        return ResponseEntity.ok(userService.save(userCreateDto,"ROLE_ADMIN",bindingResult));
    }
    @GetMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody LoginDto loginDto,
            BindingResult bindingResult
    ) {
        return ResponseEntity.ok(userService.login(loginDto,bindingResult));
    }

}
