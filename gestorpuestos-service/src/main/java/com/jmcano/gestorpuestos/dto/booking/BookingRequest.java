package com.jmcano.gestorpuestos.dto.booking;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    @NotNull
    @FutureOrPresent(message = "Booking date must be today or in the future")
    private LocalDate date;

}
