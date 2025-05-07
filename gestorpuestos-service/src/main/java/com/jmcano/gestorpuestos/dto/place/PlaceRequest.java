package com.jmcano.gestorpuestos.dto.place;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlaceRequest {

    @NotBlank
    private String name;

    @Min(0)
    private int x;

    @Min(0)
    private int y;
}
