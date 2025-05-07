package com.jmcano.gestorpuestos.dto.profile;

import lombok.Data;

@Data
public class ProfileResponse {
    private Long id;
    private String name;
    private String email;
    private Long preferredPlaceId;
    private String role;
}
