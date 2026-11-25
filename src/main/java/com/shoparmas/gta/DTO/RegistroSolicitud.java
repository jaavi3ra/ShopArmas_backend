package com.shoparmas.gta.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroSolicitud(
        @NotBlank @Size(max = 80) String nombreUsuario,
        @NotBlank @Email @Size(max = 254) String correo,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank @Size(min = 8, max = 128) String contrasena) {

    @Override
    public String toString() {
        return "RegistroSolicitud";
    }
}
