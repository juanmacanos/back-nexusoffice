package com.jmcano.gestorpuestos.dto.place;

import lombok.Data;

@Data
public class PlaceResponse {

    private Long id;

    private String name;

    private int x;

    private int y;

    private int preferredUserId;
}
