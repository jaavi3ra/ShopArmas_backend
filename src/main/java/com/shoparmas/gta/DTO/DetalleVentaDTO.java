package com.shoparmas.gta.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DetalleVentaDTO {

    private Integer idDetalleVenta;
    private Integer idVenta;
    private Integer idProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
