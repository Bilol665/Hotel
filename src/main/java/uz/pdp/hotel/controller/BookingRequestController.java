package uz.pdp.hotel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel.entity.dto.RequestDto;
import uz.pdp.hotel.entity.request.BookingRequest;
import uz.pdp.hotel.service.room.BookingRequestService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booking-request/")
public class BookingRequestController {
    private final BookingRequestService bookingRequestService;
    @PutMapping("/book")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BookingRequest> bookRoom(
            @Valid @RequestBody RequestDto requestDto,
            Principal principal,
            BindingResult bindingResult
    ) {
        return ResponseEntity.ok(bookingRequestService.book(requestDto,principal,bindingResult));
    }
    @PutMapping("/unpaid")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> unpaid(
            @RequestParam(required = false,defaultValue = "") UUID bookingId
    ) {
        return ResponseEntity.ok(bookingRequestService.moveToUnpaid(bookingId));
    }
    @PutMapping("/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> status(
            @RequestParam(required = false,defaultValue = "") String status,
            @RequestParam(required = false,defaultValue = "") UUID bookingId
    ) {
        return ResponseEntity.ok(bookingRequestService.changeStatus(status,bookingId));
    }
    @GetMapping("/getRoom")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BookingRequest> getUserRoom(
            @RequestParam(required = false,defaultValue = "") UUID bookingId,
            Principal principal
    ) {
        return ResponseEntity.ok(bookingRequestService.get(principal,bookingId));
    }
}
