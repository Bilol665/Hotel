package uz.pdp.hotel.repository.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.hotel.entity.room.RoomStatus;

import java.util.Optional;
@Repository
public interface RoomStatusRepository extends JpaRepository<RoomStatus,String> {
    Optional<RoomStatus> findRoomStatusByStatus(String status);
}
