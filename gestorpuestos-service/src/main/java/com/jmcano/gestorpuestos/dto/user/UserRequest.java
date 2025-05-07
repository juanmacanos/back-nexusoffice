package com.jmcano.gestorpuestos.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

}
