package com.shoparmas.gta.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MetodoEnvioDTO {

    private Integer idMetodoEnvio;
    private String nombreMetodo;
    private String descripcion;
    private BigDecimal costo;
    private Boolean activo;
}
