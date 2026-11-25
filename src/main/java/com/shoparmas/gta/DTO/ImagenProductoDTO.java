package com.shoparmas.gta.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ImagenProductoDTO {

    private Integer idImagen;
    private Integer idProducto;
    private String urlImagen;
    private Boolean imagenPrincipal;
    private LocalDateTime fechaRegistro;
}
