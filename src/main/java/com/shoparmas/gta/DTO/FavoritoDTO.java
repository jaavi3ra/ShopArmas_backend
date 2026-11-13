package com.shoparmas.gta.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FavoritoDTO {

    private Integer idUsuario;
    private Integer idProducto;
    private LocalDateTime fechaAgregado;
}
