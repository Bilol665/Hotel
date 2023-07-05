package uz.pdp.hotel.repository.room;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hotel.entity.request.RequestStatus;

public interface RoomBookingRequestStatusRepository extends JpaRepository<RequestStatus,String> {
}
