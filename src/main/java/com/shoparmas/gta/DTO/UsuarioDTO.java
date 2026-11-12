package com.shoparmas.gta.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data 
public class UsuarioDTO {
    private Integer idUsuario;
    private Integer idRol;
    private String nombreUsuario;
    private String correo;
    private BigDecimal saldo;
    private Integer puntosRp;
    private LocalDateTime fechaRegistro;
    private Boolean activo;
}
