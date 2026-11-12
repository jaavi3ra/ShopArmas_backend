package com.shoparmas.gta.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
@Entity
@Table(name = "Cartera")
public class Cartera {

    @Id
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @MapsId
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    //cambiar a Usuario usuario <-
    private Integer usuario;

    @Builder.Default
    @NotNull
    @PositiveOrZero
    @Digits(integer = 10, fraction = 2)
    @Column(name = "saldo", nullable = false, precision = 12, scale = 2)
    private BigDecimal saldo = new BigDecimal("100000.00");

    @Column(name = "actualizada_en", nullable = false, insertable = false, updatable = false)
    private LocalDateTime actualizadaEn;
}
