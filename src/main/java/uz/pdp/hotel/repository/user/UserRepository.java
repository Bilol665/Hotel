package uz.pdp.hotel.repository.user;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.hotel.entity.user.UserEntity;
import uz.pdp.hotel.entity.user.UserRole;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findUserEntityByEmail(String username);
    @Transactional
    @Modifying
    @Query("update users u set u.role = :role where u.id = :id")
    void block(@Param("role")UserRole role,@Param("id") UUID id);
}
