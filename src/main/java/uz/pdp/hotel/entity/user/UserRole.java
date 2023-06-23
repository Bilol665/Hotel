package uz.pdp.hotel.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import uz.pdp.hotel.entity.BaseEntity;

import java.util.List;

@Entity(name = "user_roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRole extends BaseEntity {
    private String role;
}