package uz.pdp.hotel.service.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import uz.pdp.hotel.entity.dto.UserCreateDto;
import uz.pdp.hotel.entity.user.UserEntity;
import uz.pdp.hotel.entity.user.UserRole;
import uz.pdp.hotel.exceptions.RequestValidationException;
import uz.pdp.hotel.repository.user.UserRepository;
import uz.pdp.hotel.repository.user.UserRoleRepository;
import uz.pdp.hotel.service.utils.JwtService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class UserServiceTest {
    private UserCreateDto userCreateDto;
    private UserEntity userEntity;
    private BindingResult bindingResult;
    private String email = "admin";
    private String password = "123";
    private String name = "bilol";
    private Integer age = 16;
    private Integer unpaidReqs = 0;
    private Integer canceledReqs = 0;
    private UserRole role = new UserRole("ROLE_USER");
    private Boolean isBlocked = false;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserRoleRepository userRoleRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private UserService userService;
    @BeforeEach
    void setUp(){
        initMocks(this);
        userEntity = UserEntity.builder()
                .email(email)
                .password(password)
                .name(name)
                .age(age)
                .canceledReqs(canceledReqs)
                .isBlocked(isBlocked)
                .role(role)
                .unpaidReqs(unpaidReqs)
                .build();
        userCreateDto = UserCreateDto.builder()
                .email(email)
                .age(age)
                .name(name)
                .password(password)
                .build();

    }
    @Test
    void save() {
//        when(bindingResult.hasErrors()).thenReturn(false);
        when(modelMapper.map(userCreateDto,UserEntity.class)).thenReturn(userEntity);
//        when(userCreateDto.getName().isBlank()).thenReturn(false);
        when(userRoleRepository.findUserRolesByRole(role.getRole()).size()).thenReturn(1);
        when(passwordEncoder.encode(userEntity.getPassword())).thenReturn(password);
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        UserEntity save = userService.save(userCreateDto, role.getRole(), bindingResult);
        assertEquals(email,save.getEmail());
    }
    /*@Test
    void save*/

    @Test
    void login() {
    }
}