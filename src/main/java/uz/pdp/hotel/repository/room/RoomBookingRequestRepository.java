package uz.pdp.hotel.repository.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.hotel.entity.request.BookingRequest;
import uz.pdp.hotel.entity.request.RequestStatus;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface RoomBookingRequestRepository extends JpaRepository<BookingRequest, UUID> {
    @Query("select b from bookings b where b.room.id = :id")
    Optional<BookingRequest> getByRoomId(@Param("id") UUID id);
    @Modifying
    @Query("update bookings b set b.status = :status where b.id = :id")
    void moveToUnpaid(@Param("status") RequestStatus status,@Param("id") UUID id);
}
