package uz.pdp.hotel.repository.room;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.hotel.entity.request.BookingRequest;

import java.util.List;
import java.util.UUID;
@Repository
public interface RoomBookingRequestRepository extends JpaRepository<BookingRequest, UUID> {
//    @Modifying
    @Query("select b from bookings b where b.room.size > :size and b.room.size < :size1 and b.room.type.type = :type and b.room.pricePerNight < :price")
    List<BookingRequest> getAllBySize(@Param("size") int size, @Param("size1") int size1,@Param("type") String type,@Param("price") double price, Pageable pageable);
//    @Modifying
    @Query("select b from bookings b where b.room.size > :size and b.room.size < :size1 and b.room.type.type = :type and b.room.pricePerNight < :price and b.room.hasMonitor = :hasMonitor")
    List<BookingRequest> getAllBySize(@Param("size") int size, @Param("size1") int size1,@Param("type") String type,@Param("price") double price,@Param("hasMonitor") Boolean hasMonitor, Pageable pageable);
}
