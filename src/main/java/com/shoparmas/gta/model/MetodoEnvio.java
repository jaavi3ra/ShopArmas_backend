package com.shoparmas.gta.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Entity
@Table(name = "MetodoEnvio")
public class MetodoEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metodo_envio")
    private Integer idMetodoEnvio;

    @NotNull(message = "El nombre es obligatorio")
    @Size(max = 100)
    @Column(name = "nombre_metodo", nullable = false, unique = true, length = 100)
    private String nombreMetodo;

    @Size(max = 255)
    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Builder.Default
    @NotNull
    @PositiveOrZero
    @Digits(integer = 10, fraction = 2)
    @Column(name = "costo", nullable = false, precision = 12, scale = 2)
    private BigDecimal costo = BigDecimal.ZERO;

    @Builder.Default
    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}
