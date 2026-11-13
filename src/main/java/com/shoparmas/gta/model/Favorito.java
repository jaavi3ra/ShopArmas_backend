package com.shoparmas.gta.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Favorito")
public class Favorito {

    @Builder.Default
    @EmbeddedId
    private FavoritoID id = new FavoritoID();

    @MapsId("idUsuario")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @MapsId("idProducto")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "fecha_agregado", nullable = false, insertable = false, updatable = false)
    private LocalDateTime fechaAgregado;
}