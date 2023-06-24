package uz.pdp.hotel.repository.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.hotel.entity.room.RoomType;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, UUID> {
    Optional<RoomType> findRoomTypeByType(String type);
}
