package uz.pdp.hotel.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.hotel.entity.dto.LoginDto;
import uz.pdp.hotel.entity.dto.UserCreateDto;
import uz.pdp.hotel.entity.response.JwtResponse;
import uz.pdp.hotel.entity.user.UserEntity;
import uz.pdp.hotel.entity.user.UserRole;
import uz.pdp.hotel.exceptions.DataNotFoundException;
import uz.pdp.hotel.exceptions.FailedAuthorizeException;
import uz.pdp.hotel.repository.UserRepository;
import uz.pdp.hotel.service.utils.JwtService;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserEntity save(UserCreateDto userCreateDto) {
        UserEntity map = modelMapper.map(userCreateDto, UserEntity.class);
        map.setRole(new UserRole("USER"));
        map.setPassword(passwordEncoder.encode(map.getPassword()));
        return userRepository.save(map);
    }

    public JwtResponse login(LoginDto loginDto) {
        UserEntity userEntity=userRepository.findUserEntityByEmail(loginDto.getUsername())
                .orElseThrow(()-> new DataNotFoundException("User not found"));
        if(passwordEncoder.matches(loginDto.getPassword(),userEntity.getPassword())) {
            String accessToken=jwtService.generateAccessToken(userEntity);
            return JwtResponse.builder().accessToken(accessToken).build();
        }
        throw new FailedAuthorizeException("User status unpaid or password is incorrect !");
    }
}
