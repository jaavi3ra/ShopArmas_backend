package com.shoparmas.gta.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductoDTO {

    private Integer idProducto;
    private String nombreProducto;
    private String slug;
    private String descripcion;
    private BigDecimal precio;
    private Integer puntosRp;
    private Integer stock;
    private Integer idEstadoArma;
    private Integer idSubcategoria;
    private Byte dano;
    private Byte precisionArma;
    private Byte alcance;
    private Boolean esDrop;
    private LocalDateTime fechaRegistro;
    private Boolean activo;
}
