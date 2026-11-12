package com.shoparmas.gta.model;


import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class FavoritoID implements Serializable {

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "id_producto", nullable = false)
    private Integer idProducto;
}