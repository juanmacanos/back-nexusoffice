package com.jmcano.gestorpuestos.dto.user;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;

    private String name;

    private String role;
    private String email;
}
