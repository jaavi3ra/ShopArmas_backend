package com.shoparmas.gta.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import lombok.Data;

@Data 
public class CarteraDTO {
  
    private Integer idUsuario;
    private UsuarioDTO usuario;
    private BigDecimal saldo;
    private LocalDateTime actualizadaEn;   
}
