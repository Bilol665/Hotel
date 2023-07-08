package uz.pdp.hotel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @PutMapping("/unpaid/{bookingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> unpaid(
            @PathVariable UUID bookingId
    ) {
        return ResponseEntity.ok(bookingRequestService.moveToUnpaid(bookingId));
    }
    @PutMapping("/status/{bookingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> status(
            @RequestParam(required = false,defaultValue = "") String status,
            @PathVariable UUID bookingId
    ) {
        return ResponseEntity.ok(bookingRequestService.changeStatus(status,bookingId));
    }
    @GetMapping("/getRoom/{bookingId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BookingRequest> getUserRoom(
            @PathVariable UUID bookingId,
            Principal principal
    ) {
        return ResponseEntity.ok(bookingRequestService.get(principal,bookingId));
    }
    @PutMapping("/cancel/{bookingId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<HttpStatus> cancel(
            @PathVariable UUID bookingId,
            Principal principal
    ) {
        bookingRequestService.cancel(bookingId,principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/pay/{bookingId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<HttpStatus> pay(
            @PathVariable UUID bookingId,
            Principal principal
    ) {
        bookingRequestService.pay(bookingId,principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
