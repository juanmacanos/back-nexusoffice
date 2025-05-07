package com.jmcano.gestorpuestos.dto.booking;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingResponse {

    private Long id;
    private Long userId;
    private Long placeId;
    private LocalDate date;

}
