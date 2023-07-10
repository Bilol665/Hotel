package uz.pdp.hotel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel.entity.dto.RoomCreateDto;
import uz.pdp.hotel.entity.request.BookingRequest;
import uz.pdp.hotel.entity.room.RoomEntity;
import uz.pdp.hotel.service.room.BookingRequestService;
import uz.pdp.hotel.service.room.RoomService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room")
public class RoomController {
    private final BookingRequestService bookingRequestService;
    private final RoomService roomService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomEntity> addRoom(
            @Valid @RequestBody RoomCreateDto roomCreateDto,
            BindingResult bindingResult
    ) {
        return ResponseEntity.ok(roomService.save(roomCreateDto,bindingResult));
    }
    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RoomEntity>> getAll(
            @RequestParam(required = false,defaultValue = "2") int size,
            @RequestParam(required = false,defaultValue = "0") double price,
            @RequestParam(required = false,defaultValue = "",name = "has_monitor") Boolean hasMonitor,
            @RequestParam(required = false,defaultValue = "") String date,
            @RequestParam(required = false,defaultValue = "0",name = "room_type") String roomType,
            @RequestParam(required = false,defaultValue = "false") Boolean isEmpty,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "10",name = "page_size") int pageSize
    ){
        return ResponseEntity.ok(roomService.getAll(size,price,hasMonitor,date,roomType,isEmpty,page, pageSize));
    }
    @GetMapping("/get/{roomId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getRoom(
            @PathVariable UUID roomId
    ){
        return ResponseEntity.ok(bookingRequestService.get(roomId));
    }
}
