package uz.pdp.hotel.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import uz.pdp.hotel.entity.dto.LoginDto;
import uz.pdp.hotel.entity.dto.UserCreateDto;
import uz.pdp.hotel.entity.response.JwtResponse;
import uz.pdp.hotel.entity.user.UserEntity;
import uz.pdp.hotel.entity.user.UserRole;
import uz.pdp.hotel.exceptions.DataNotFoundException;
import uz.pdp.hotel.exceptions.FailedAuthorizeException;
import uz.pdp.hotel.exceptions.RequestValidationException;
import uz.pdp.hotel.repository.user.UserRepository;
import uz.pdp.hotel.repository.user.UserRoleRepository;
import uz.pdp.hotel.service.utils.JwtService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserEntity save(UserCreateDto userCreateDto, String role, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        UserEntity map = modelMapper.map(userCreateDto, UserEntity.class);
        if(userCreateDto.getName().isBlank())
            map.setName(map.getEmail());
        int size = userRoleRepository.findUserRolesByRole(role).size();
        if(size == 1)
            map.setRole(userRoleRepository.findUserRolesByRole(role).get(0));
        else if(size == 0)
            map.setRole(new UserRole(role));
        map.setPassword(passwordEncoder.encode(map.getPassword()));
        map.setCanceledReqs(0);
        map.setUnpaidReqs(0);
        map.setIsBlocked(false);
        return userRepository.save(map);
    }

    public JwtResponse login(LoginDto loginDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        UserEntity userEntity=userRepository.findUserEntityByEmail(loginDto.getEmail())
                .orElseThrow(()-> new DataNotFoundException("User not found"));
        if(userEntity.getIsBlocked() || userEntity.getCanceledReqs()>=3 || userEntity.getUnpaidReqs()>=3)
            throw new FailedAuthorizeException("Your are blocked!");
        if(passwordEncoder.matches(loginDto.getPassword(),userEntity.getPassword())) {
            String accessToken=jwtService.generateAccessToken(userEntity);
            return JwtResponse.builder().accessToken(accessToken).build();
        }
        throw new FailedAuthorizeException("Password is incorrect!");
    }
}
