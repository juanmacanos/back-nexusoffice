package com.jmcano.gestorpuestos.dto.placeAvailability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceAvailabilityResponse {
    private Long placeId;
    private String name;
    private int x;
    private int y;
    private boolean occupied;
    private Long userId;
    private String userName;
    private Long preferredUserId;
    private LocalDate date;
}
