package com.shoparmas.gta.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data 
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @NotNull
    @Size(max = 80)
    @Column(name = "nombre_usuario", nullable = false, length = 80)
    private String nombreUsuario;

    @NotNull
    @Email
    @Size(max = 254)
    @Column(name = "correo", nullable = false, unique = true, length = 254)
    private String correo;

    @NotNull
    @Size(max = 255)
    @Column(name = "contrasena_hash", nullable = false, length = 255)
    private String contrasenaHash;

    @Builder.Default
    @NotNull
    @PositiveOrZero
    @Digits(integer = 10, fraction = 2)
    @Column(name = "saldo", nullable = false, precision = 12, scale = 2)
    private BigDecimal saldo = new BigDecimal("100000.00");

    @Builder.Default
    @NotNull
    @PositiveOrZero
    @Column(name = "puntos_rp", nullable = false)
    private Integer puntosRp = 0;

    @Column(name = "fecha_registro", nullable = false, insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Builder.Default
    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}