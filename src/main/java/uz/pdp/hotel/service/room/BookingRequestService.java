package uz.pdp.hotel.service.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import uz.pdp.hotel.entity.dto.RequestDto;
import uz.pdp.hotel.entity.request.BookingRequest;
import uz.pdp.hotel.entity.request.RequestStatus;
import uz.pdp.hotel.entity.room.RoomEntity;
import uz.pdp.hotel.entity.room.RoomStatus;
import uz.pdp.hotel.entity.user.UserEntity;
import uz.pdp.hotel.exceptions.DataNotFoundException;
import uz.pdp.hotel.exceptions.NotAcceptable;
import uz.pdp.hotel.exceptions.RequestValidationException;
import uz.pdp.hotel.repository.room.RoomBookingRequestRepository;
import uz.pdp.hotel.repository.room.RoomBookingRequestStatusRepository;
import uz.pdp.hotel.repository.room.RoomRepository;
import uz.pdp.hotel.repository.room.RoomStatusRepository;
import uz.pdp.hotel.repository.user.UserRepository;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingRequestService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final RoomStatusRepository roomStatusRepository;
    private final RoomBookingRequestRepository roomBookingRequestRepository;
    private final RoomBookingRequestStatusRepository roomBookingRequestStatusRepository;
    public Object get(UUID roomId) {
        if(roomId == null)
            throw new DataNotFoundException("Id is not given!");
        RoomEntity roomEntity = roomRepository.findById(roomId).orElseThrow(
                () -> new DataNotFoundException("Room not found!")
        );
        if(Objects.equals(roomEntity.getStatus(),roomStatusRepository.findById("EMPTY").orElseGet(
                () -> roomStatusRepository.save(new RoomStatus("EMPTY"))
        )))
            return roomEntity;
        return roomBookingRequestRepository.getByRoomId(roomId).orElseThrow(
                () -> new DataNotFoundException("Booking not found!")
        );
    }

    public BookingRequest book(RequestDto requestDto, Principal principal, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Date endDate;
        try {
             endDate = dateFormat.parse(requestDto.getEndDate());
        } catch (ParseException e) {
            throw new NotAcceptable("Invalid date format");
        }
        RoomEntity roomEntity = roomRepository.findById(requestDto.getRoomId()).orElseThrow(
                () -> new DataNotFoundException("Room not found!")
        );
        roomEntity.setStatus(roomStatusRepository.findById("BUSY").orElseGet(
                () -> roomStatusRepository.save(new RoomStatus("BUSY"))
        ));
        RequestStatus paid = roomBookingRequestStatusRepository.findById("PAID").orElseGet(
                () -> roomBookingRequestStatusRepository.save(new RequestStatus("PAID"))
        );
        long diffInMillis = endDate.getTime() - beginDate.getTime();
        long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);
        BookingRequest bookingRequest = BookingRequest.builder()
                .user(userRepository.findUserEntityByEmail(principal.getName()).orElseThrow(
                        () -> new DataNotFoundException("User not found!")
                ))
                .room(roomEntity)
                .totalPrice(roomEntity.getPricePerNight()*diffInDays)
                .status(paid)
                .beginDate(beginDate)
                .endDate(endDate)
                .build();
        return roomBookingRequestRepository.save(bookingRequest);
    }


    public Boolean moveToUnpaid(UUID bookingId) {
        if(bookingId == null)
            throw new DataNotFoundException("Id is not given!");
        try{
            if(Objects.equals(roomBookingRequestRepository.findById(bookingId).orElseThrow(() -> new DataNotFoundException("Booking not found!")).getStatus(),
                    roomBookingRequestStatusRepository.findById("CANCELED").orElseThrow()))
                throw new NotAcceptable("Sorry but your booking was canceled");
            roomBookingRequestRepository.moveToUnpaid(roomBookingRequestStatusRepository.findById("UNPAID").orElseGet(
                    () -> roomBookingRequestStatusRepository.save(new RequestStatus("UNPAID"))
            ), bookingId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Boolean changeStatus(String status,UUID bookingId) {
        if(bookingId == null)
            throw new DataNotFoundException("Id is not given!");
        if(status.isBlank())
            throw new DataNotFoundException("New status is not given!");
        try{
            roomBookingRequestRepository.moveToUnpaid(roomBookingRequestStatusRepository.findById(status).orElseGet(
                    () -> roomBookingRequestStatusRepository.save(new RequestStatus(status))
            ), bookingId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public BookingRequest get(Principal principal,UUID bookingId) {
        if(bookingId == null)
            throw new DataNotFoundException("Id is not given!");
        BookingRequest bookingRequest = roomBookingRequestRepository.findById(bookingId).orElseThrow(
                () -> new DataNotFoundException("Booking not found!")
        );
        UserEntity userEntity = userRepository.findUserEntityByEmail(principal.getName()).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        );
        if(!Objects.equals(bookingRequest.getUser(),userEntity)) {
            throw new DataNotFoundException("Booking not found!");
        }
        if(Objects.equals(bookingRequest.getStatus(),roomBookingRequestStatusRepository.findById("CANCELED").orElseThrow()))
            throw new NotAcceptable("Your booking was canceled");
        return bookingRequest;
    }

    public void cancel(UUID bookingId, Principal principal) {
        BookingRequest bookingRequest = roomBookingRequestRepository.findById(bookingId).orElseThrow(
                () -> new DataNotFoundException("Booking not found!")
        );
        if(!Objects.equals(bookingRequest.getUser().getEmail(),principal.getName()))
            throw new NotAcceptable("You cannot cancel this booking!");
        bookingRequest.setStatus(roomBookingRequestStatusRepository.findById("CANCELED").orElseGet(
                () -> roomBookingRequestStatusRepository.save(new RequestStatus("CANCELED"))
        ));
        roomBookingRequestRepository.save(bookingRequest);
    }

    public void pay(UUID bookingId, Principal principal) {
        BookingRequest bookingRequest = roomBookingRequestRepository.findById(bookingId).orElseThrow(
                () -> new DataNotFoundException("Booking not found!")
        );
        if(!Objects.equals(bookingRequest.getUser().getEmail(),principal.getName()))
            throw new NotAcceptable("You cannot pay by the reason that it is not your booking!");
        bookingRequest.setStatus(roomBookingRequestStatusRepository.findById("PAID").orElseGet(
                () -> roomBookingRequestStatusRepository.save(new RequestStatus("PAID"))
        ));
        roomBookingRequestRepository.save(bookingRequest);
    }
}
