package com.shoparmas.gta.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @NotNull
    @Size(max = 150)
    @Column(name = "nombre_producto", nullable = false, length = 150)
    private String nombreProducto;

    @NotNull
    @Size(max = 160)
    @Column(name = "slug", nullable = false, unique = true, length = 160)
    private String slug;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 10, fraction = 2)
    @Column(name = "precio", nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;

    @Builder.Default
    @NotNull
    @PositiveOrZero
    @Column(name = "puntos_rp", nullable = false)
    private Integer puntosRp = 0;

    @Builder.Default
    @NotNull
    @PositiveOrZero
    @Column(name = "stock", nullable = false)
    private Integer stock = 0;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_estado_arma", nullable = false)
    private EstadoArma estadoArma;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_subcategoria", nullable = false)
    private Subcategoria subcategoria;

    @Builder.Default
    @NotNull
    @Min(0)
    @Max(100)
    @Column(name = "dano", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Byte dano = 0;

    @Builder.Default
    @NotNull
    @Min(0)
    @Max(100)
    @Column(name = "precision_arma", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Byte precisionArma = 0;

    @Builder.Default
    @NotNull
    @Min(0)
    @Max(100)
    @Column(name = "alcance", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Byte alcance = 0;

    @Builder.Default
    @NotNull
    @Column(name = "es_drop", nullable = false)
    private Boolean esDrop = false;

    @Column(name = "fecha_registro", nullable = false, insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Builder.Default
    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}