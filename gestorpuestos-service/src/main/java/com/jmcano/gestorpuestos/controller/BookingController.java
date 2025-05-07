package com.jmcano.gestorpuestos.controller;

import com.jmcano.gestorpuestos.dto.booking.BookingRequest;
import com.jmcano.gestorpuestos.dto.booking.BookingResponse;
import com.jmcano.gestorpuestos.dto.placeAvailability.PlaceAvailabilityResponse;
import com.jmcano.gestorpuestos.model.User;
import com.jmcano.gestorpuestos.service.BookingService;
import com.jmcano.gestorpuestos.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/assist")
    public ResponseEntity<BookingResponse> assist(
            @RequestBody @Valid BookingRequest bookingRequest, Authentication authentication
    ) {
        String userName = (String) authentication.getPrincipal();
        BookingResponse bookingResponse = bookingService.assist(userName, bookingRequest.getDate());

        URI location = URI.create("/api/bookings" + bookingResponse.getId());
        return ResponseEntity.created(location).body(bookingResponse);
    }

    @PostMapping("/cancelAssist")
    public ResponseEntity<Void> cancelAssist(
            @RequestBody @Valid BookingRequest bookingRequest,
            Authentication authentication
    ) {
        String userName = (String) authentication.getPrincipal();
        bookingService.cancelAssist(userName, bookingRequest.getDate());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> findAll() {
        return ResponseEntity.ok(bookingService.findAll());
    }

    @GetMapping("/history")
    public ResponseEntity<List<BookingResponse>> getBookingHistory(Authentication authentication){
        String username = (String) authentication.getPrincipal();
        return ResponseEntity.ok(bookingService.getBookingHistory(username));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<BookingResponse>> getBookingHistoryByUsername(@PathVariable Long userId){
        return ResponseEntity.ok(bookingService.getBookingHistoryByUser(userId));
    }

    @GetMapping("/availability")
    public ResponseEntity<List<PlaceAvailabilityResponse>> getAvailability(@RequestParam LocalDate date) {
        return ResponseEntity.ok(bookingService.getAvailability(date));
    }

}
