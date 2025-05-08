package com.jmcano.gestorpuestos.dto.placeAvailability;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceAvailabilityResponse {
    private Long placeId;
    private String name;
    private int x;
    private int y;
    private boolean occupied;
    private Long userId;
    private String userName;
    private Long preferredUserId;
}
