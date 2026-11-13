package com.shoparmas.gta.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class VentaDTO {

    private Integer idVenta;
    private Integer idUsuario;
    private Integer idDireccion;
    private Integer idEstadoVenta;
    private Integer idMetodoEnvio;
    private LocalDateTime fechaVenta;
    private BigDecimal subtotal;
    private BigDecimal costoEnvio;
    private BigDecimal total;
}
