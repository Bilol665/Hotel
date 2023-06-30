package uz.pdp.hotel.repository.room;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.hotel.entity.room.RoomEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {

    @Query("select r from rooms r where r.size > :size and r.size < :size1 and r.type.type = :type and r.pricePerNight < :price and r.hasMonitor = :hasMonitor")
    List<RoomEntity> getAllBySize(@Param("size") int size, @Param("size1") int size1, @Param("type") String type, @Param("price") double price, @Param("hasMonitor") Boolean hasMonitor, Pageable pageable);
}
