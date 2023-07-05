package uz.pdp.hotel.service.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import uz.pdp.hotel.entity.dto.RequestDto;
import uz.pdp.hotel.entity.request.BookingRequest;
import uz.pdp.hotel.entity.request.RequestStatus;
import uz.pdp.hotel.entity.room.RoomEntity;
import uz.pdp.hotel.exceptions.DataNotFoundException;
import uz.pdp.hotel.exceptions.NotAcceptable;
import uz.pdp.hotel.exceptions.RequestValidationException;
import uz.pdp.hotel.repository.room.RoomBookingRequestRepository;
import uz.pdp.hotel.repository.room.RoomBookingRequestStatusRepository;
import uz.pdp.hotel.repository.room.RoomRepository;
import uz.pdp.hotel.repository.user.UserRepository;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingRequestService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final RoomBookingRequestRepository roomBookingRequestRepository;
    private final RoomBookingRequestStatusRepository roomBookingRequestStatusRepository;

    public BookingRequest get(UUID roomId) {
        return roomBookingRequestRepository.getByRoomId(roomId).orElseThrow(
                () -> new DataNotFoundException("Booking not found!")
        );
    }

    public BookingRequest book(RequestDto requestDto, Principal principal, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date endDate;
        Date beginDate = new Date();
        try {
             endDate = dateFormat.parse(requestDto.getEndDate());
        } catch (ParseException e) {
            throw new NotAcceptable("Invalid date format");
        }
        RoomEntity roomEntity = roomRepository.findById(requestDto.getRoomId()).orElseThrow(
                () -> new DataNotFoundException("Room not found!")
        );
        Optional<RequestStatus> paid = roomBookingRequestStatusRepository.findById("PAID");
        RequestStatus paid1 = paid.orElseGet(() -> roomBookingRequestStatusRepository.save(new RequestStatus("PAID")));
        long diffInMillis = endDate.getTime() - beginDate.getTime();
        long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);
        BookingRequest bookingRequest = BookingRequest.builder()
                .user(userRepository.findUserEntityByEmail(principal.getName()).orElseThrow(
                        () -> new DataNotFoundException("User not found!")
                ))
                .room(roomEntity)
                .totalPrice(roomEntity.getPricePerNight()*diffInDays)
                .status(paid1)
                .beginDate(beginDate)
                .endDate(endDate)
                .build();
        return roomBookingRequestRepository.save(bookingRequest);
    }
}
